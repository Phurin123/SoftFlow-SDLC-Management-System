package com.softflow.service;

import com.softflow.dto.request.ErTableRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ErTableResponse;
import com.softflow.dto.response.RequirementRefResponse;
import com.softflow.entity.ErTable;
import com.softflow.entity.Project;
import com.softflow.entity.Requirement;
import com.softflow.enums.ErStatus;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.ErTableRepository;
import com.softflow.repository.ProjectRepository;
import com.softflow.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErTableService {

    private final ErTableRepository erTableRepository;
    private final ProjectRepository projectRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementService requirementService;

    @Transactional
    public ApiResponse<ErTableResponse> createErTable(ErTableRequest request) {
        log.info("Creating ER Table: {} for project: {}", request.getTableName(), request.getProjectId());

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

        ErTable erTable = ErTable.builder()
                .project(project)
                .tableName(request.getTableName())
                .displayName(request.getDisplayName())
                .description(request.getDescription())
                .relatedDfdDataStore(request.getRelatedDfdDataStore())
                .status(ErStatus.DRAFT)
                .relatedRequirements(requirements)
                .build();

        ErTable saved = erTableRepository.save(erTable);
        return ApiResponse.success(mapToResponse(saved), "ER Table created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<ErTableResponse> getErTableById(UUID id) {
        ErTable erTable = erTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ER Table not found"));
        return ApiResponse.success(mapToResponse(erTable));
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ErTableResponse>> getErTablesByProject(UUID projectId) {
        List<ErTable> tables = erTableRepository.findByProjectId(projectId);
        List<ErTableResponse> response = tables.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional
    public ApiResponse<ErTableResponse> updateErTable(UUID id, ErTableRequest request) {
        ErTable erTable = erTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ER Table not found"));

        erTable.setTableName(request.getTableName());
        erTable.setDisplayName(request.getDisplayName());
        erTable.setDescription(request.getDescription());
        erTable.setRelatedDfdDataStore(request.getRelatedDfdDataStore());

        ErTable updated = erTableRepository.save(erTable);
        return ApiResponse.success(mapToResponse(updated), "ER Table updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteErTable(UUID id) {
        ErTable erTable = erTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ER Table not found"));
        erTableRepository.delete(erTable);
        return ApiResponse.success(null, "ER Table deleted successfully");
    }

    private ErTableResponse mapToResponse(ErTable erTable) {
        List<RequirementRefResponse> reqRefs = erTable.getRelatedRequirements() != null
                ? erTable.getRelatedRequirements().stream()
                        .map(r -> RequirementRefResponse.builder()
                                .id(r.getId())
                                .requirementCode(r.getRequirementCode())
                                .title(r.getTitle())
                                .build())
                        .collect(Collectors.toList())
                : new ArrayList<>();

        return ErTableResponse.builder()
                .id(erTable.getId())
                .projectId(erTable.getProject().getId())
                .projectName(erTable.getProject().getProjectName())
                .tableName(erTable.getTableName())
                .displayName(erTable.getDisplayName())
                .description(erTable.getDescription())
                .relatedDfdDataStore(erTable.getRelatedDfdDataStore())
                .status(erTable.getStatus())
                .relatedRequirements(reqRefs)
                .columnCount(erTable.getColumns() != null ? erTable.getColumns().size() : 0)
                .createdAt(erTable.getCreatedAt())
                .updatedAt(erTable.getUpdatedAt())
                .build();
    }
}
