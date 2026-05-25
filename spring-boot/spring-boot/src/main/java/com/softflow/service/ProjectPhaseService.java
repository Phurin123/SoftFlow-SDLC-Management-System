package com.softflow.service;

import com.softflow.dto.request.ProjectPhaseRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.MilestoneResponse;
import com.softflow.dto.response.ProjectPhaseResponse;
import com.softflow.entity.Project;
import com.softflow.entity.ProjectPhase;
import com.softflow.enums.PhaseStatus;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.ProjectPhaseRepository;
import com.softflow.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectPhaseService {

    private final ProjectPhaseRepository phaseRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ApiResponse<ProjectPhaseResponse> createPhase(ProjectPhaseRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        ProjectPhase phase = ProjectPhase.builder()
                .project(project)
                .phaseName(request.getPhaseName())
                .description(request.getDescription())
                .sequence(request.getSequence())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .owner(request.getOwner())
                .status(request.getStatus() != null ? request.getStatus() : PhaseStatus.NOT_STARTED)
                .build();

        if (request.getDependsOnPhaseId() != null) {
            ProjectPhase dependsOn = phaseRepository.findById(request.getDependsOnPhaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dependency phase not found"));
            phase.setDependsOnPhase(dependsOn);
        }

        ProjectPhase saved = phaseRepository.save(phase);
        return ApiResponse.success(mapToResponse(saved), "Phase created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ProjectPhaseResponse>> getPhasesByProject(UUID projectId) {
        List<ProjectPhase> phases = phaseRepository.findByProjectIdOrderBySequenceAsc(projectId);
        List<ProjectPhaseResponse> response = phases.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<ProjectPhaseResponse> getPhaseById(UUID id) {
        ProjectPhase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase not found"));
        return ApiResponse.success(mapToResponse(phase));
    }

    @Transactional
    public ApiResponse<ProjectPhaseResponse> updatePhase(UUID id, ProjectPhaseRequest request) {
        ProjectPhase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase not found"));

        phase.setPhaseName(request.getPhaseName());
        phase.setDescription(request.getDescription());
        phase.setSequence(request.getSequence());
        phase.setStartDate(request.getStartDate());
        phase.setEndDate(request.getEndDate());
        phase.setOwner(request.getOwner());
        if (request.getStatus() != null) {
            phase.setStatus(request.getStatus());
        }

        ProjectPhase updated = phaseRepository.save(phase);
        return ApiResponse.success(mapToResponse(updated), "Phase updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deletePhase(UUID id) {
        ProjectPhase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase not found"));
        phaseRepository.delete(phase);
        return ApiResponse.success(null, "Phase deleted successfully");
    }

    private ProjectPhaseResponse mapToResponse(ProjectPhase phase) {
        return ProjectPhaseResponse.builder()
                .id(phase.getId())
                .projectId(phase.getProject().getId())
                .projectName(phase.getProject().getProjectName())
                .phaseName(phase.getPhaseName())
                .description(phase.getDescription())
                .sequence(phase.getSequence())
                .startDate(phase.getStartDate())
                .endDate(phase.getEndDate())
                .owner(phase.getOwner())
                .status(phase.getStatus())
                .progressPercentage(phase.getProgressPercentage())
                .dependsOnPhaseId(phase.getDependsOnPhase() != null ? phase.getDependsOnPhase().getId() : null)
                .dependsOnPhaseName(phase.getDependsOnPhase() != null ? phase.getDependsOnPhase().getPhaseName() : null)
                .createdAt(phase.getCreatedAt())
                .milestoneCount(phase.getMilestones() != null ? phase.getMilestones().size() : 0)
                .build();
    }
}
