package com.softflow.controller;

import com.softflow.dto.request.ErColumnRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ErColumnResponse;
import com.softflow.service.ErColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/er-columns")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ErColumnController {

    private final ErColumnService erColumnService;

    @PostMapping
    public ResponseEntity<ApiResponse<ErColumnResponse>> createErColumn(@Valid @RequestBody ErColumnRequest request) {
        return ResponseEntity.ok(erColumnService.createErColumn(request));
    }

    @GetMapping("/table/{erTableId}")
    public ResponseEntity<ApiResponse<List<ErColumnResponse>>> getColumnsByTable(@PathVariable UUID erTableId) {
        return ResponseEntity.ok(erColumnService.getColumnsByTable(erTableId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ErColumnResponse>> getErColumn(@PathVariable UUID id) {
        return ResponseEntity.ok(erColumnService.getErColumnById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ErColumnResponse>> updateErColumn(
            @PathVariable UUID id, @Valid @RequestBody ErColumnRequest request) {
        return ResponseEntity.ok(erColumnService.updateErColumn(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteErColumn(@PathVariable UUID id) {
        return ResponseEntity.ok(erColumnService.deleteErColumn(id));
    }
}
