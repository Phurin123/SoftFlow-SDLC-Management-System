package com.softflow.controller;

import com.softflow.dto.request.ProjectPhaseRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ProjectPhaseResponse;
import com.softflow.service.ProjectPhaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/phases")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectPhaseController {

    private final ProjectPhaseService phaseService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectPhaseResponse>> createPhase(@Valid @RequestBody ProjectPhaseRequest request) {
        return ResponseEntity.ok(phaseService.createPhase(request));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<ProjectPhaseResponse>>> getPhasesByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(phaseService.getPhasesByProject(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectPhaseResponse>> getPhase(@PathVariable UUID id) {
        return ResponseEntity.ok(phaseService.getPhaseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectPhaseResponse>> updatePhase(
            @PathVariable UUID id, @Valid @RequestBody ProjectPhaseRequest request) {
        return ResponseEntity.ok(phaseService.updatePhase(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePhase(@PathVariable UUID id) {
        return ResponseEntity.ok(phaseService.deletePhase(id));
    }
}
