package com.softflow.service;

import com.softflow.dto.request.RequirementApprovalRequest;
import com.softflow.dto.request.RequirementRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.RequirementApprovalResponse;
import com.softflow.dto.response.RequirementResponse;
import com.softflow.entity.Project;
import com.softflow.entity.Requirement;
import com.softflow.entity.RequirementApproval;
import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.RequirementStatus;
import com.softflow.exception.BusinessRuleViolationException;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.ProjectRepository;
import com.softflow.repository.RequirementApprovalRepository;
import com.softflow.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Requirement Service with Business Rule Gate enforcement.
 * 
 * Gate Rule: Requirement must have BA_CONFIRMED and CUSTOMER_CONFIRMED
 * before it can be used for DFD/ER/Specification creation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RequirementService {

    private final RequirementRepository requirementRepository;
    private final RequirementApprovalRepository approvalRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ApiResponse<RequirementResponse> createRequirement(RequirementRequest request) {
        log.info("Creating requirement: {} for project: {}", request.getRequirementCode(), request.getProjectId());

        if (requirementRepository.existsByRequirementCode(request.getRequirementCode())) {
            return ApiResponse.error("Requirement with this code already exists");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Requirement requirement = Requirement.builder()
                .project(project)
                .requirementCode(request.getRequirementCode())
                .title(request.getTitle())
                .description(request.getDescription())
                .requirementType(request.getRequirementType())
                .source(request.getSource())
                .priority(request.getPriority())
                .businessValue(request.getBusinessValue())
                .acceptanceCriteria(request.getAcceptanceCriteria())
                .relatedFiles(request.getRelatedFiles())
                .baConfirmStatus(ApprovalStatus.PENDING)
                .customerConfirmStatus(ApprovalStatus.PENDING)
                .versionRequirement("1.0")
                .status(RequirementStatus.DRAFT)
                .build();

        Requirement saved = requirementRepository.save(requirement);
        return ApiResponse.success(mapToResponse(saved), "Requirement created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<RequirementResponse> getRequirementById(UUID id) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));
        return ApiResponse.success(mapToResponse(requirement));
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<RequirementResponse>> getRequirementsByProject(UUID projectId) {
        List<Requirement> requirements = requirementRepository.findByProjectId(projectId);
        List<RequirementResponse> response = requirements.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<RequirementResponse>> getFullyApprovedRequirements(UUID projectId) {
        List<Requirement> requirements = requirementRepository.findFullyApproved(
                projectId, ApprovalStatus.CONFIRMED, ApprovalStatus.CONFIRMED);
        List<RequirementResponse> response = requirements.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    /**
     * Business Rule Gate Check: Validates if a requirement is fully approved.
     * Throws BusinessRuleViolationException if not approved.
     */
    @Transactional(readOnly = true)
    public void validateRequirementApproved(UUID requirementId) {
        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));

        if (requirement.getBaConfirmStatus() != ApprovalStatus.CONFIRMED) {
            throw new BusinessRuleViolationException(
                    "Requirement Gate: Requirement " + requirement.getRequirementCode() + 
                    " is not yet approved by BA. Current status: " + requirement.getBaConfirmStatus());
        }

        if (requirement.getCustomerConfirmStatus() != ApprovalStatus.CONFIRMED) {
            throw new BusinessRuleViolationException(
                    "Requirement Gate: Requirement " + requirement.getRequirementCode() + 
                    " is not yet approved by Customer. Current status: " + requirement.getCustomerConfirmStatus());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<Long> countPendingApprovals(UUID projectId) {
        long count = requirementRepository.countPendingApprovals(projectId);
        return ApiResponse.success(count);
    }

    @Transactional
    public ApiResponse<RequirementResponse> updateRequirement(UUID id, RequirementRequest request) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));

        // If already approved, restrict certain changes
        boolean wasApproved = requirement.getBaConfirmStatus() == ApprovalStatus.CONFIRMED 
                && requirement.getCustomerConfirmStatus() == ApprovalStatus.CONFIRMED;

        requirement.setTitle(request.getTitle());
        requirement.setDescription(request.getDescription());
        requirement.setRequirementType(request.getRequirementType());
        requirement.setSource(request.getSource());
        requirement.setPriority(request.getPriority());
        requirement.setBusinessValue(request.getBusinessValue());
        requirement.setAcceptanceCriteria(request.getAcceptanceCriteria());
        requirement.setRelatedFiles(request.getRelatedFiles());

        // If requirement was approved and content changes, reset approvals
        if (wasApproved && contentChanged(requirement, request)) {
            requirement.setBaConfirmStatus(ApprovalStatus.PENDING);
            requirement.setCustomerConfirmStatus(ApprovalStatus.PENDING);
            requirement.setStatus(RequirementStatus.CHANGED);
            log.info("Requirement {} content changed, resetting approvals", requirement.getRequirementCode());
        }

        Requirement updated = requirementRepository.save(requirement);
        return ApiResponse.success(mapToResponse(updated), "Requirement updated successfully");
    }

    @Transactional
    public ApiResponse<RequirementResponse> submitForApproval(UUID id) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));

        requirement.setStatus(RequirementStatus.IN_REVIEW);
        Requirement updated = requirementRepository.save(requirement);
        return ApiResponse.success(mapToResponse(updated), "Requirement submitted for approval");
    }

    @Transactional
    public ApiResponse<RequirementApprovalResponse> addApproval(RequirementApprovalRequest request) {
        Requirement requirement = requirementRepository.findById(request.getRequirementId())
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));

        RequirementApproval approval = RequirementApproval.builder()
                .requirement(requirement)
                .approverRole(request.getApproverRole())
                .approverName(request.getApproverName())
                .approvalStatus(request.getApprovalStatus())
                .comment(request.getComment())
                .approvedAt(request.getApprovalStatus() == ApprovalStatus.CONFIRMED ? LocalDateTime.now() : null)
                .build();

        RequirementApproval saved = approvalRepository.save(approval);

        // Update requirement approval status based on role
        if ("BA".equalsIgnoreCase(request.getApproverRole())) {
            requirement.setBaConfirmStatus(request.getApprovalStatus());
        } else if ("CUSTOMER".equalsIgnoreCase(request.getApproverRole())) {
            requirement.setCustomerConfirmStatus(request.getApprovalStatus());
        }

        // If both approved, update status
        if (requirement.getBaConfirmStatus() == ApprovalStatus.CONFIRMED 
                && requirement.getCustomerConfirmStatus() == ApprovalStatus.CONFIRMED) {
            requirement.setStatus(RequirementStatus.APPROVED);
        } else if (request.getApprovalStatus() == ApprovalStatus.REJECTED) {
            requirement.setStatus(RequirementStatus.DRAFT);
        }

        requirementRepository.save(requirement);

        return ApiResponse.success(mapApprovalToResponse(saved), "Approval recorded successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<RequirementApprovalResponse>> getApprovalHistory(UUID requirementId) {
        List<RequirementApproval> approvals = approvalRepository.findByRequirementIdOrderByCreatedAtDesc(requirementId);
        List<RequirementApprovalResponse> response = approvals.stream()
                .map(this::mapApprovalToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional
    public ApiResponse<Void> deleteRequirement(UUID id) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));
        requirementRepository.delete(requirement);
        return ApiResponse.success(null, "Requirement deleted successfully");
    }

    private boolean contentChanged(Requirement req, RequirementRequest request) {
        return !req.getTitle().equals(request.getTitle()) 
                || !req.getDescription().equals(request.getDescription());
    }

    private RequirementResponse mapToResponse(Requirement requirement) {
        boolean fullyApproved = requirement.getBaConfirmStatus() == ApprovalStatus.CONFIRMED 
                && requirement.getCustomerConfirmStatus() == ApprovalStatus.CONFIRMED;

        return RequirementResponse.builder()
                .id(requirement.getId())
                .projectId(requirement.getProject().getId())
                .projectName(requirement.getProject().getProjectName())
                .requirementCode(requirement.getRequirementCode())
                .title(requirement.getTitle())
                .description(requirement.getDescription())
                .requirementType(requirement.getRequirementType())
                .source(requirement.getSource())
                .priority(requirement.getPriority())
                .businessValue(requirement.getBusinessValue())
                .acceptanceCriteria(requirement.getAcceptanceCriteria())
                .relatedFiles(requirement.getRelatedFiles())
                .baConfirmStatus(requirement.getBaConfirmStatus())
                .customerConfirmStatus(requirement.getCustomerConfirmStatus())
                .versionRequirement(requirement.getVersionRequirement())
                .status(requirement.getStatus())
                .createdAt(requirement.getCreatedAt())
                .updatedAt(requirement.getUpdatedAt())
                .createdBy(requirement.getCreatedBy())
                .isFullyApproved(fullyApproved)
                .build();
    }

    private RequirementApprovalResponse mapApprovalToResponse(RequirementApproval approval) {
        return RequirementApprovalResponse.builder()
                .id(approval.getId())
                .requirementId(approval.getRequirement().getId())
                .requirementCode(approval.getRequirement().getRequirementCode())
                .approverRole(approval.getApproverRole())
                .approverName(approval.getApproverName())
                .approvalStatus(approval.getApprovalStatus())
                .comment(approval.getComment())
                .approvedAt(approval.getApprovedAt())
                .createdAt(approval.getCreatedAt())
                .build();
    }
}
