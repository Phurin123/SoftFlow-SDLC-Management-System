package com.softflow.controller;

import com.softflow.dto.request.RequirementApprovalRequest;
import com.softflow.dto.request.RequirementRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.RequirementApprovalResponse;
import com.softflow.dto.response.RequirementResponse;
import com.softflow.service.RequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/requirements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequirementController {

    private final RequirementService requirementService;

    @PostMapping
    public ResponseEntity<ApiResponse<RequirementResponse>> createRequirement(@Valid @RequestBody RequirementRequest request) {
        return ResponseEntity.ok(requirementService.createRequirement(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RequirementResponse>> getRequirement(@PathVariable UUID id) {
        return ResponseEntity.ok(requirementService.getRequirementById(id));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<RequirementResponse>>> getRequirementsByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(requirementService.getRequirementsByProject(projectId));
    }

    @GetMapping("/project/{projectId}/approved")
    public ResponseEntity<ApiResponse<List<RequirementResponse>>> getFullyApprovedRequirements(@PathVariable UUID projectId) {
        return ResponseEntity.ok(requirementService.getFullyApprovedRequirements(projectId));
    }

    @GetMapping("/project/{projectId}/pending-count")
    public ResponseEntity<ApiResponse<Long>> countPendingApprovals(@PathVariable UUID projectId) {
        return ResponseEntity.ok(requirementService.countPendingApprovals(projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RequirementResponse>> updateRequirement(
            @PathVariable UUID id, @Valid @RequestBody RequirementRequest request) {
        return ResponseEntity.ok(requirementService.updateRequirement(id, request));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<RequirementResponse>> submitForApproval(@PathVariable UUID id) {
        return ResponseEntity.ok(requirementService.submitForApproval(id));
    }

    @PostMapping("/approvals")
    public ResponseEntity<ApiResponse<RequirementApprovalResponse>> addApproval(
            @Valid @RequestBody RequirementApprovalRequest request) {
        return ResponseEntity.ok(requirementService.addApproval(request));
    }

    @GetMapping("/{id}/approvals")
    public ResponseEntity<ApiResponse<List<RequirementApprovalResponse>>> getApprovalHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(requirementService.getApprovalHistory(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRequirement(@PathVariable UUID id) {
        return ResponseEntity.ok(requirementService.deleteRequirement(id));
    }
}
