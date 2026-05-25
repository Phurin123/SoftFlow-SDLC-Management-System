package com.softflow.service;

import com.softflow.dto.request.ContractRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.ContractResponse;
import com.softflow.dto.response.PaginatedResponse;
import com.softflow.entity.Contract;
import com.softflow.entity.Customer;
import com.softflow.entity.Project;
import com.softflow.enums.ContractSignStatus;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.ContractRepository;
import com.softflow.repository.CustomerRepository;
import com.softflow.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ApiResponse<ContractResponse> createContract(ContractRequest request) {
        log.info("Creating contract: {}", request.getContractNo());

        if (contractRepository.existsByContractNo(request.getContractNo())) {
            return ApiResponse.error("Contract with this number already exists");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        }

        Contract contract = Contract.builder()
                .contractNo(request.getContractNo())
                .customer(customer)
                .project(project)
                .contractType(request.getContractType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .contractValue(request.getContractValue())
                .paymentTerms(request.getPaymentTerms())
                .scopeSummary(request.getScopeSummary())
                .contractFileUrl(request.getContractFileUrl())
                .signStatus(request.getSignStatus() != null ? request.getSignStatus() : ContractSignStatus.DRAFT)
                .remark(request.getRemark())
                .build();

        Contract saved = contractRepository.save(contract);
        return ApiResponse.success(mapToResponse(saved), "Contract created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<ContractResponse> getContractById(UUID id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
        return ApiResponse.success(mapToResponse(contract));
    }

    @Transactional(readOnly = true)
    public ApiResponse<PaginatedResponse<ContractResponse>> getAllContracts(int page, int size, String search, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Contract> contractPage;
        if (search != null && !search.isEmpty()) {
            contractPage = contractRepository.searchContracts(search, pageable);
        } else {
            contractPage = contractRepository.findAll(pageable);
        }

        List<ContractResponse> content = contractPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        PaginatedResponse<ContractResponse> response = PaginatedResponse.<ContractResponse>builder()
                .content(content)
                .page(contractPage.getNumber())
                .size(contractPage.getSize())
                .totalElements(contractPage.getTotalElements())
                .totalPages(contractPage.getTotalPages())
                .first(contractPage.isFirst())
                .last(contractPage.isLast())
                .empty(contractPage.isEmpty())
                .build();

        return ApiResponse.success(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ContractResponse>> getContractsByCustomer(UUID customerId) {
        List<Contract> contracts = contractRepository.findByCustomerId(customerId);
        List<ContractResponse> response = contracts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional
    public ApiResponse<ContractResponse> updateContract(UUID id, ContractRequest request) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        contract.setContractType(request.getContractType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setContractValue(request.getContractValue());
        contract.setPaymentTerms(request.getPaymentTerms());
        contract.setScopeSummary(request.getScopeSummary());
        contract.setContractFileUrl(request.getContractFileUrl());
        contract.setRemark(request.getRemark());

        Contract updated = contractRepository.save(contract);
        return ApiResponse.success(mapToResponse(updated), "Contract updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteContract(UUID id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
        contractRepository.delete(contract);
        return ApiResponse.success(null, "Contract deleted successfully");
    }

    private ContractResponse mapToResponse(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .contractNo(contract.getContractNo())
                .customerId(contract.getCustomer().getId())
                .customerName(contract.getCustomer().getCustomerName())
                .projectId(contract.getProject() != null ? contract.getProject().getId() : null)
                .projectName(contract.getProject() != null ? contract.getProject().getProjectName() : null)
                .contractType(contract.getContractType())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .contractValue(contract.getContractValue())
                .paymentTerms(contract.getPaymentTerms())
                .scopeSummary(contract.getScopeSummary())
                .contractFileUrl(contract.getContractFileUrl())
                .signStatus(contract.getSignStatus())
                .renewalStatus(contract.getRenewalStatus())
                .remark(contract.getRemark())
                .createdAt(contract.getCreatedAt())
                .updatedAt(contract.getUpdatedAt())
                .build();
    }
}
