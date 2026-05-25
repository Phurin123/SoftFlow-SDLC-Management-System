package com.softflow.service;

import com.softflow.dto.request.DfdProcessRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.DfdProcessResponse;
import com.softflow.dto.response.RequirementRefResponse;
import com.softflow.entity.DfdProcess;
import com.softflow.entity.Project;
import com.softflow.entity.Requirement;
import com.softflow.enums.DfdStatus;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.DfdProcessRepository;
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
public class DfdProcessService {

    private final DfdProcessRepository dfdProcessRepository;
    private final ProjectRepository projectRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementService requirementService;

    @Transactional
    public ApiResponse<DfdProcessResponse> createDfdProcess(DfdProcessRequest request) {
        log.info("Creating DFD process: {} for project: {}", request.getProcessCode(), request.getProjectId());

        if (dfdProcessRepository.existsByProcessCode(request.getProcessCode())) {
            return ApiResponse.error("DFD Process with this code already exists");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // Validate all related requirements are approved (Business Rule Gate)
        List<Requirement> requirements = new ArrayList<>();
        if (request.getRelatedRequirementIds() != null) {
            for (UUID reqId : request.getRelatedRequirementIds()) {
                requirementService.validateRequirementApproved(reqId);
                Requirement req = requirementRepository.findById(reqId)
                        .orElseThrow(() -> new ResourceNotFoundException("Requirement not found: " + reqId));
                requirements.add(req);
            }
        }

        DfdProcess process = DfdProcess.builder()
                .project(project)
                .processCode(request.getProcessCode())
                .processName(request.getProcessName())
                .description(request.getDescription())
                .inputData(request.getInputData())
                .outputData(request.getOutputData())
                .relatedDataStore(request.getRelatedDataStore())
                .owner(request.getOwner())
                .dfdLevel(request.getDfdLevel())
                .status(DfdStatus.DRAFT)
                .relatedRequirements(requirements)
                .build();

        DfdProcess saved = dfdProcessRepository.save(process);
        return ApiResponse.success(mapToResponse(saved), "DFD Process created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<DfdProcessResponse> getDfdProcessById(UUID id) {
        DfdProcess process = dfdProcessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DFD Process not found"));
        return ApiResponse.success(mapToResponse(process));
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<DfdProcessResponse>> getDfdProcessesByProject(UUID projectId) {
        List<DfdProcess> processes = dfdProcessRepository.findByProjectId(projectId);
        List<DfdProcessResponse> response = processes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional
    public ApiResponse<DfdProcessResponse> updateDfdProcess(UUID id, DfdProcessRequest request) {
        DfdProcess process = dfdProcessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DFD Process not found"));

        process.setProcessName(request.getProcessName());
        process.setDescription(request.getDescription());
        process.setInputData(request.getInputData());
        process.setOutputData(request.getOutputData());
        process.setRelatedDataStore(request.getRelatedDataStore());
        process.setOwner(request.getOwner());
        process.setDfdLevel(request.getDfdLevel());

        DfdProcess updated = dfdProcessRepository.save(process);
        return ApiResponse.success(mapToResponse(updated), "DFD Process updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteDfdProcess(UUID id) {
        DfdProcess process = dfdProcessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DFD Process not found"));
        dfdProcessRepository.delete(process);
        return ApiResponse.success(null, "DFD Process deleted successfully");
    }

    private DfdProcessResponse mapToResponse(DfdProcess process) {
        List<RequirementRefResponse> reqRefs = process.getRelatedRequirements() != null 
                ? process.getRelatedRequirements().stream()
                        .map(r -> RequirementRefResponse.builder()
                                .id(r.getId())
                                .requirementCode(r.getRequirementCode())
                                .title(r.getTitle())
                                .build())
                        .collect(Collectors.toList())
                : new ArrayList<>();

        return DfdProcessResponse.builder()
                .id(process.getId())
                .projectId(process.getProject().getId())
                .projectName(process.getProject().getProjectName())
                .processCode(process.getProcessCode())
                .processName(process.getProcessName())
                .description(process.getDescription())
                .inputData(process.getInputData())
                .outputData(process.getOutputData())
                .relatedDataStore(process.getRelatedDataStore())
                .owner(process.getOwner())
                .status(process.getStatus())
                .dfdLevel(process.getDfdLevel())
                .relatedRequirements(reqRefs)
                .createdAt(process.getCreatedAt())
                .updatedAt(process.getUpdatedAt())
                .build();
    }
}
