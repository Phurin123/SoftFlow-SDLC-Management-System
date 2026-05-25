package com.softflow.controller;

import com.softflow.dto.request.SpecificationRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.SpecificationResponse;
import com.softflow.enums.ApprovalStatus;
import com.softflow.service.SpecificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/specifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SpecificationController {

    private final SpecificationService specificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<SpecificationResponse>> createSpecification(@Valid @RequestBody SpecificationRequest request) {
        return ResponseEntity.ok(specificationService.createSpecification(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecificationResponse>> getSpecification(@PathVariable UUID id) {
        return ResponseEntity.ok(specificationService.getSpecificationById(id));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<SpecificationResponse>>> getSpecificationsByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(specificationService.getSpecificationsByProject(projectId));
    }

    @GetMapping("/project/{projectId}/approved")
    public ResponseEntity<ApiResponse<List<SpecificationResponse>>> getFullyApprovedSpecifications(@PathVariable UUID projectId) {
        return ResponseEntity.ok(specificationService.getFullyApprovedSpecifications(projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecificationResponse>> updateSpecification(
            @PathVariable UUID id, @Valid @RequestBody SpecificationRequest request) {
        return ResponseEntity.ok(specificationService.updateSpecification(id, request));
    }

    @PatchMapping("/{id}/head-approval")
    public ResponseEntity<ApiResponse<SpecificationResponse>> updateHeadApproval(
            @PathVariable UUID id,
            @RequestParam ApprovalStatus status,
            @RequestParam(required = false) String comment) {
        return ResponseEntity.ok(specificationService.updateHeadApproval(id, status, comment));
    }

    @PatchMapping("/{id}/customer-approval")
    public ResponseEntity<ApiResponse<SpecificationResponse>> updateCustomerApproval(
            @PathVariable UUID id,
            @RequestParam ApprovalStatus status,
            @RequestParam(required = false) String comment) {
        return ResponseEntity.ok(specificationService.updateCustomerApproval(id, status, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSpecification(@PathVariable UUID id) {
        return ResponseEntity.ok(specificationService.deleteSpecification(id));
    }
}
