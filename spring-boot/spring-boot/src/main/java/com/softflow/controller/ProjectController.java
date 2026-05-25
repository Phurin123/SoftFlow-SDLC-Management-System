package com.softflow.controller;

import com.softflow.dto.request.ProjectRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.PaginatedResponse;
import com.softflow.dto.response.ProjectDashboardResponse;
import com.softflow.dto.response.ProjectResponse;
import com.softflow.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProject(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<ProjectResponse>>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(projectService.getAllProjects(page, size, search, sortBy, sortDir));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjectsByCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(projectService.getProjectsByCustomer(customerId));
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<ApiResponse<ProjectDashboardResponse>> getProjectDashboard(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectDashboard(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable UUID id, @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
}
