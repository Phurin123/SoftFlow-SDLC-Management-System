package com.softflow.controller;

import com.softflow.dto.request.MilestoneRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.MilestoneResponse;
import com.softflow.service.MilestoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/milestones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<ApiResponse<MilestoneResponse>> createMilestone(@Valid @RequestBody MilestoneRequest request) {
        return ResponseEntity.ok(milestoneService.createMilestone(request));
    }

    @GetMapping("/phase/{phaseId}")
    public ResponseEntity<ApiResponse<List<MilestoneResponse>>> getMilestonesByPhase(@PathVariable UUID phaseId) {
        return ResponseEntity.ok(milestoneService.getMilestonesByPhase(phaseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MilestoneResponse>> getMilestone(@PathVariable UUID id) {
        return ResponseEntity.ok(milestoneService.getMilestoneById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MilestoneResponse>> updateMilestone(
            @PathVariable UUID id, @Valid @RequestBody MilestoneRequest request) {
        return ResponseEntity.ok(milestoneService.updateMilestone(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMilestone(@PathVariable UUID id) {
        return ResponseEntity.ok(milestoneService.deleteMilestone(id));
    }
}
