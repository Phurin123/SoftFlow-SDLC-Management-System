package com.softflow.service;

import com.softflow.dto.request.SpecificationRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.RequirementRefResponse;
import com.softflow.dto.response.SpecificationResponse;
import com.softflow.entity.Project;
import com.softflow.entity.Requirement;
import com.softflow.entity.Specification;
import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.SpecificationStatus;
import com.softflow.exception.BusinessRuleViolationException;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.ProjectRepository;
import com.softflow.repository.RequirementRepository;
import com.softflow.repository.SpecificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Specification Service with Business Rule Gate enforcement.
 * 
 * Gate Rule: Specification must have HEAD_APPROVED and CUSTOMER_APPROVED
 * before Development Tasks can be created.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SpecificationService {

    private final SpecificationRepository specificationRepository;
    private final ProjectRepository projectRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementService requirementService;

    @Transactional
    public ApiResponse<SpecificationResponse> createSpecification(SpecificationRequest request) {
        log.info("Creating Specification: {} for project: {}", request.getSpecCode(), request.getProjectId());

        if (specificationRepository.existsBySpecCode(request.getSpecCode())) {
            return ApiResponse.error("Specification with this code already exists");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // Validate related requirements are approved
        List<Requirement> requirements = new ArrayList<>();
        if (request.getRelatedRequirementIds() != null) {
            for (UUID reqId : request.getRelatedRequirementIds()) {
                requirementService.validateRequirementApproved(reqId);
                Requirement req = requirementRepository.findById(reqId)
                        .orElseThrow(() -> new ResourceNotFoundException("Requirement not found: " + reqId));
                requirements.add(req);
            }
        }

        Specification specification = Specification.builder()
                .project(project)
                .specCode(request.getSpecCode())
                .screenName(request.getScreenName())
                .specType(request.getSpecType())
                .description(request.getDescription())
                .uiActions(request.getUiActions())
                .validationRules(request.getValidationRules())
                .permissions(request.getPermissions())
                .estimatedManday(request.getEstimatedManday())
                .dependency(request.getDependency())
                .headConfirmStatus(ApprovalStatus.PENDING)
                .customerConfirmStatus(ApprovalStatus.PENDING)
                .status(SpecificationStatus.DRAFT)
                .versionSpecification("1.0")
                .relatedRequirements(requirements)
                .build();

        Specification saved = specificationRepository.save(specification);
        return ApiResponse.success(mapToResponse(saved), "Specification created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<SpecificationResponse> getSpecificationById(UUID id) {
        Specification spec = specificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specification not found"));
        return ApiResponse.success(mapToResponse(spec));
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<SpecificationResponse>> getSpecificationsByProject(UUID projectId) {
        List<Specification> specs = specificationRepository.findByProjectId(projectId);
        List<SpecificationResponse> response = specs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    /**
     * Business Rule Gate Check: Validates if a specification is fully approved.
     * Throws BusinessRuleViolationException if not approved.
     */
    @Transactional(readOnly = true)
    public void validateSpecificationApproved(UUID specificationId) {
        Specification spec = specificationRepository.findById(specificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Specification not found"));

        if (spec.getHeadConfirmStatus() != ApprovalStatus.CONFIRMED) {
            throw new BusinessRuleViolationException(
                    "Specification Gate: Specification " + spec.getSpecCode() + 
                    " is not yet approved by Head. Current status: " + spec.getHeadConfirmStatus());
        }

        if (spec.getCustomerConfirmStatus() != ApprovalStatus.CONFIRMED) {
            throw new BusinessRuleViolationException(
                    "Specification Gate: Specification " + spec.getSpecCode() + 
                    " is not yet approved by Customer. Current status: " + spec.getCustomerConfirmStatus());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<SpecificationResponse>> getFullyApprovedSpecifications(UUID projectId) {
        List<Specification> specs = specificationRepository.findByProjectId(projectId);
        List<SpecificationResponse> approved = specs.stream()
                .filter(s -> s.getHeadConfirmStatus() == ApprovalStatus.CONFIRMED 
                        && s.getCustomerConfirmStatus() == ApprovalStatus.CONFIRMED)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(approved);
    }

    @Transactional
    public ApiResponse<SpecificationResponse> updateSpecification(UUID id, SpecificationRequest request) {
        Specification spec = specificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specification not found"));

        spec.setScreenName(request.getScreenName());
        spec.setSpecType(request.getSpecType());
        spec.setDescription(request.getDescription());
        spec.setUiActions(request.getUiActions());
        spec.setValidationRules(request.getValidationRules());
        spec.setPermissions(request.getPermissions());
        spec.setEstimatedManday(request.getEstimatedManday());
        spec.setDependency(request.getDependency());

        Specification updated = specificationRepository.save(spec);
        return ApiResponse.success(mapToResponse(updated), "Specification updated successfully");
    }

    @Transactional
    public ApiResponse<SpecificationResponse> updateHeadApproval(UUID id, ApprovalStatus status, String comment) {
        Specification spec = specificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specification not found"));

        spec.setHeadConfirmStatus(status);
        if (status == ApprovalStatus.CONFIRMED && spec.getCustomerConfirmStatus() == ApprovalStatus.CONFIRMED) {
            spec.setStatus(SpecificationStatus.CUSTOMER_APPROVED);
        } else if (status == ApprovalStatus.REJECTED) {
            spec.setStatus(SpecificationStatus.REJECTED);
        }

        Specification updated = specificationRepository.save(spec);
        return ApiResponse.success(mapToResponse(updated), "Head approval status updated");
    }

    @Transactional
    public ApiResponse<SpecificationResponse> updateCustomerApproval(UUID id, ApprovalStatus status, String comment) {
        Specification spec = specificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specification not found"));

        spec.setCustomerConfirmStatus(status);
        if (status == ApprovalStatus.CONFIRMED && spec.getHeadConfirmStatus() == ApprovalStatus.CONFIRMED) {
            spec.setStatus(SpecificationStatus.CUSTOMER_APPROVED);
        } else if (status == ApprovalStatus.REJECTED) {
            spec.setStatus(SpecificationStatus.REJECTED);
        }

        Specification updated = specificationRepository.save(spec);
        return ApiResponse.success(mapToResponse(updated), "Customer approval status updated");
    }

    @Transactional
    public ApiResponse<Void> deleteSpecification(UUID id) {
        Specification spec = specificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specification not found"));
        specificationRepository.delete(spec);
        return ApiResponse.success(null, "Specification deleted successfully");
    }

    private SpecificationResponse mapToResponse(Specification spec) {
        boolean fullyApproved = spec.getHeadConfirmStatus() == ApprovalStatus.CONFIRMED 
                && spec.getCustomerConfirmStatus() == ApprovalStatus.CONFIRMED;

        List<RequirementRefResponse> reqRefs = spec.getRelatedRequirements() != null
                ? spec.getRelatedRequirements().stream()
                        .map(r -> RequirementRefResponse.builder()
                                .id(r.getId())
                                .requirementCode(r.getRequirementCode())
                                .title(r.getTitle())
                                .build())
                        .collect(Collectors.toList())
                : new ArrayList<>();

        return SpecificationResponse.builder()
                .id(spec.getId())
                .projectId(spec.getProject().getId())
                .projectName(spec.getProject().getProjectName())
                .specCode(spec.getSpecCode())
                .screenName(spec.getScreenName())
                .specType(spec.getSpecType())
                .description(spec.getDescription())
                .uiActions(spec.getUiActions())
                .validationRules(spec.getValidationRules())
                .permissions(spec.getPermissions())
                .estimatedManday(spec.getEstimatedManday())
                .dependency(spec.getDependency())
                .headConfirmStatus(spec.getHeadConfirmStatus())
                .customerConfirmStatus(spec.getCustomerConfirmStatus())
                .status(spec.getStatus())
                .versionSpecification(spec.getVersion())
                .relatedRequirements(reqRefs)
                .isFullyApproved(fullyApproved)
                .createdAt(spec.getCreatedAt())
                .updatedAt(spec.getUpdatedAt())
                .build();
    }
}
