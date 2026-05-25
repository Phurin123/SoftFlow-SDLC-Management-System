package com.softflow.controller;

import com.softflow.dto.request.ErTableRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ErTableResponse;
import com.softflow.service.ErTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/er-tables")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ErTableController {

    private final ErTableService erTableService;

    @PostMapping
    public ResponseEntity<ApiResponse<ErTableResponse>> createErTable(@Valid @RequestBody ErTableRequest request) {
        return ResponseEntity.ok(erTableService.createErTable(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ErTableResponse>> getErTable(@PathVariable UUID id) {
        return ResponseEntity.ok(erTableService.getErTableById(id));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<ErTableResponse>>> getErTablesByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(erTableService.getErTablesByProject(projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ErTableResponse>> updateErTable(
            @PathVariable UUID id, @Valid @RequestBody ErTableRequest request) {
        return ResponseEntity.ok(erTableService.updateErTable(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteErTable(@PathVariable UUID id) {
        return ResponseEntity.ok(erTableService.deleteErTable(id));
    }
}
