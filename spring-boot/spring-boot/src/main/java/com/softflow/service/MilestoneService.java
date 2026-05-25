package com.softflow.service;

import com.softflow.dto.request.MilestoneRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.MilestoneResponse;
import com.softflow.entity.Milestone;
import com.softflow.entity.ProjectPhase;
import com.softflow.enums.PhaseStatus;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.MilestoneRepository;
import com.softflow.repository.ProjectPhaseRepository;
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
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectPhaseRepository phaseRepository;

    @Transactional
    public ApiResponse<MilestoneResponse> createMilestone(MilestoneRequest request) {
        ProjectPhase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Phase not found"));

        Milestone milestone = Milestone.builder()
                .phase(phase)
                .milestoneName(request.getMilestoneName())
                .description(request.getDescription())
                .sequence(request.getSequence())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .owner(request.getOwner())
                .status(request.getStatus() != null ? request.getStatus() : PhaseStatus.NOT_STARTED)
                .deliverables(request.getDeliverables())
                .build();

        Milestone saved = milestoneRepository.save(milestone);
        return ApiResponse.success(mapToResponse(saved), "Milestone created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<MilestoneResponse>> getMilestonesByPhase(UUID phaseId) {
        List<Milestone> milestones = milestoneRepository.findByPhaseIdOrderBySequenceAsc(phaseId);
        List<MilestoneResponse> response = milestones.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<MilestoneResponse> getMilestoneById(UUID id) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));
        return ApiResponse.success(mapToResponse(milestone));
    }

    @Transactional
    public ApiResponse<MilestoneResponse> updateMilestone(UUID id, MilestoneRequest request) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));

        milestone.setMilestoneName(request.getMilestoneName());
        milestone.setDescription(request.getDescription());
        milestone.setSequence(request.getSequence());
        milestone.setStartDate(request.getStartDate());
        milestone.setEndDate(request.getEndDate());
        milestone.setOwner(request.getOwner());
        if (request.getStatus() != null) {
            milestone.setStatus(request.getStatus());
        }
        milestone.setDeliverables(request.getDeliverables());

        Milestone updated = milestoneRepository.save(milestone);
        return ApiResponse.success(mapToResponse(updated), "Milestone updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteMilestone(UUID id) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));
        milestoneRepository.delete(milestone);
        return ApiResponse.success(null, "Milestone deleted successfully");
    }

    private MilestoneResponse mapToResponse(Milestone milestone) {
        return MilestoneResponse.builder()
                .id(milestone.getId())
                .phaseId(milestone.getPhase().getId())
                .phaseName(milestone.getPhase().getPhaseName())
                .milestoneName(milestone.getMilestoneName())
                .description(milestone.getDescription())
                .sequence(milestone.getSequence())
                .startDate(milestone.getStartDate())
                .endDate(milestone.getEndDate())
                .owner(milestone.getOwner())
                .status(milestone.getStatus())
                .progressPercentage(milestone.getProgressPercentage())
                .deliverables(milestone.getDeliverables())
                .createdAt(milestone.getCreatedAt())
                .build();
    }
}
