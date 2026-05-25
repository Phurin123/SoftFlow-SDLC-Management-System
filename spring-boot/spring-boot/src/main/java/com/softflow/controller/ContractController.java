package com.softflow.controller;

import com.softflow.dto.request.ContractRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ContractResponse;
import com.softflow.dto.response.PaginatedResponse;
import com.softflow.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/contracts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContractResponse>> createContract(@Valid @RequestBody ContractRequest request) {
        return ResponseEntity.ok(contractService.createContract(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContractResponse>> getContract(@PathVariable UUID id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<ContractResponse>>> getAllContracts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(contractService.getAllContracts(page, size, search, sortBy, sortDir));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getContractsByCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(contractService.getContractsByCustomer(customerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContractResponse>> updateContract(
            @PathVariable UUID id, @Valid @RequestBody ContractRequest request) {
        return ResponseEntity.ok(contractService.updateContract(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContract(@PathVariable UUID id) {
        return ResponseEntity.ok(contractService.deleteContract(id));
    }
}
