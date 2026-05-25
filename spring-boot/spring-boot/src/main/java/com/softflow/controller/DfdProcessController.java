package com.softflow.controller;

import com.softflow.dto.request.DfdProcessRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.DfdProcessResponse;
import com.softflow.service.DfdProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/dfd-processes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DfdProcessController {

    private final DfdProcessService dfdProcessService;

    @PostMapping
    public ResponseEntity<ApiResponse<DfdProcessResponse>> createDfdProcess(@Valid @RequestBody DfdProcessRequest request) {
        return ResponseEntity.ok(dfdProcessService.createDfdProcess(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DfdProcessResponse>> getDfdProcess(@PathVariable UUID id) {
        return ResponseEntity.ok(dfdProcessService.getDfdProcessById(id));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<DfdProcessResponse>>> getDfdProcessesByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(dfdProcessService.getDfdProcessesByProject(projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DfdProcessResponse>> updateDfdProcess(
            @PathVariable UUID id, @Valid @RequestBody DfdProcessRequest request) {
        return ResponseEntity.ok(dfdProcessService.updateDfdProcess(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDfdProcess(@PathVariable UUID id) {
        return ResponseEntity.ok(dfdProcessService.deleteDfdProcess(id));
    }
}
