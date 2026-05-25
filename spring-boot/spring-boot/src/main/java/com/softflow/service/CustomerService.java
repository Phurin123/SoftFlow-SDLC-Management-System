package com.softflow.service;

import com.softflow.dto.request.CustomerRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.CustomerResponse;
import com.softflow.dto.response.PaginatedResponse;
import com.softflow.entity.Customer;
import com.softflow.enums.CustomerStatus;
import com.softflow.exception.ResourceNotFoundException;
import com.softflow.repository.CustomerRepository;
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
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public ApiResponse<CustomerResponse> createCustomer(CustomerRequest request) {
        log.info("Creating customer: {}", request.getCustomerName());

        if (request.getTaxId() != null && customerRepository.existsByTaxId(request.getTaxId())) {
            return ApiResponse.error("Customer with this Tax ID already exists");
        }

        Customer customer = Customer.builder()
                .customerName(request.getCustomerName())
                .taxId(request.getTaxId())
                .address(request.getAddress())
                .contactPerson(request.getContactPerson())
                .phone(request.getPhone())
                .email(request.getEmail())
                .lineId(request.getLineId())
                .customerType(request.getCustomerType())
                .status(request.getStatus() != null ? request.getStatus() : CustomerStatus.ACTIVE)
                .remark(request.getRemark())
                .build();

        Customer saved = customerRepository.save(customer);
        return ApiResponse.success(mapToResponse(saved), "Customer created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<CustomerResponse> getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return ApiResponse.success(mapToResponse(customer));
    }

    @Transactional(readOnly = true)
    public ApiResponse<PaginatedResponse<CustomerResponse>> getAllCustomers(int page, int size, String search, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Customer> customerPage;
        if (search != null && !search.isEmpty()) {
            customerPage = customerRepository.searchCustomers(search, pageable);
        } else {
            customerPage = customerRepository.findAll(pageable);
        }

        List<CustomerResponse> content = customerPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        PaginatedResponse<CustomerResponse> paginatedResponse = PaginatedResponse.<CustomerResponse>builder()
                .content(content)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .first(customerPage.isFirst())
                .last(customerPage.isLast())
                .empty(customerPage.isEmpty())
                .build();

        return ApiResponse.success(paginatedResponse);
    }

    @Transactional
    public ApiResponse<CustomerResponse> updateCustomer(UUID id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customer.setCustomerName(request.getCustomerName());
        customer.setTaxId(request.getTaxId());
        customer.setAddress(request.getAddress());
        customer.setContactPerson(request.getContactPerson());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setLineId(request.getLineId());
        customer.setCustomerType(request.getCustomerType());
        if (request.getStatus() != null) {
            customer.setStatus(request.getStatus());
        }
        customer.setRemark(request.getRemark());

        Customer updated = customerRepository.save(customer);
        return ApiResponse.success(mapToResponse(updated), "Customer updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
        return ApiResponse.success(null, "Customer deleted successfully");
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerName(customer.getCustomerName())
                .taxId(customer.getTaxId())
                .address(customer.getAddress())
                .contactPerson(customer.getContactPerson())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .lineId(customer.getLineId())
                .customerType(customer.getCustomerType())
                .status(customer.getStatus())
                .remark(customer.getRemark())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .projectCount(customer.getProjects() != null ? customer.getProjects().size() : 0)
                .contractCount(customer.getContracts() != null ? customer.getContracts().size() : 0)
                .build();
    }
}
