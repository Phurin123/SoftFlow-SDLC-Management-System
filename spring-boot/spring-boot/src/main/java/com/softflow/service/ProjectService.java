package com.softflow.service;

import com.softflow.dto.request.ProjectRequest;
import com.softflow.dto.response.ApiResponse;
import com.softflow.dto.response.PaginatedResponse;
import com.softflow.dto.response.ProjectDashboardResponse;
import com.softflow.dto.response.ProjectResponse;
import com.softflow.entity.Contract;
import com.softflow.entity.Customer;
import com.softflow.entity.Project;
import com.softflow.enums.SdlcStatus;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final ContractRepository contractRepository;

    @Transactional
    public ApiResponse<ProjectResponse> createProject(ProjectRequest request) {
        log.info("Creating project: {}", request.getProjectCode());

        if (projectRepository.existsByProjectCode(request.getProjectCode())) {
            return ApiResponse.error("Project with this code already exists");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Contract contract = null;
        if (request.getContractId() != null) {
            contract = contractRepository.findById(request.getContractId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
        }

        Project project = Project.builder()
                .projectCode(request.getProjectCode())
                .projectName(request.getProjectName())
                .customer(customer)
                .contract(contract)
                .projectManager(request.getProjectManager())
                .businessAnalyst(request.getBusinessAnalyst())
                .systemAnalyst(request.getSystemAnalyst())
                .startDate(request.getStartDate())
                .plannedEndDate(request.getPlannedEndDate())
                .budgetManday(request.getBudgetManday())
                .sdlcStatus(request.getSdlcStatus() != null ? request.getSdlcStatus() : SdlcStatus.PROSPECT)
                .priority(request.getPriority())
                .description(request.getDescription())
                .build();

        Project saved = projectRepository.save(project);
        return ApiResponse.success(mapToResponse(saved), "Project created successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<ProjectResponse> getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return ApiResponse.success(mapToResponse(project));
    }

    @Transactional(readOnly = true)
    public ApiResponse<PaginatedResponse<ProjectResponse>> getAllProjects(int page, int size, String search, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Project> projectPage;
        if (search != null && !search.isEmpty()) {
            projectPage = projectRepository.searchProjects(search, pageable);
        } else {
            projectPage = projectRepository.findAll(pageable);
        }

        List<ProjectResponse> content = projectPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        PaginatedResponse<ProjectResponse> response = PaginatedResponse.<ProjectResponse>builder()
                .content(content)
                .page(projectPage.getNumber())
                .size(projectPage.getSize())
                .totalElements(projectPage.getTotalElements())
                .totalPages(projectPage.getTotalPages())
                .first(projectPage.isFirst())
                .last(projectPage.isLast())
                .empty(projectPage.isEmpty())
                .build();

        return ApiResponse.success(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ProjectResponse>> getProjectsByCustomer(UUID customerId) {
        List<Project> projects = projectRepository.findByCustomerId(customerId);
        List<ProjectResponse> response = projects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @Transactional
    public ApiResponse<ProjectResponse> updateProject(UUID id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        project.setProjectName(request.getProjectName());
        project.setProjectManager(request.getProjectManager());
        project.setBusinessAnalyst(request.getBusinessAnalyst());
        project.setSystemAnalyst(request.getSystemAnalyst());
        project.setStartDate(request.getStartDate());
        project.setPlannedEndDate(request.getPlannedEndDate());
        project.setBudgetManday(request.getBudgetManday());
        if (request.getSdlcStatus() != null) {
            project.setSdlcStatus(request.getSdlcStatus());
        }
        if (request.getPriority() != null) {
            project.setPriority(request.getPriority());
        }
        project.setDescription(request.getDescription());

        if (request.getContractId() != null) {
            Contract contract = contractRepository.findById(request.getContractId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
            project.setContract(contract);
        }

        Project updated = projectRepository.save(project);
        return ApiResponse.success(mapToResponse(updated), "Project updated successfully");
    }

    @Transactional
    public ApiResponse<Void> deleteProject(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        projectRepository.delete(project);
        return ApiResponse.success(null, "Project deleted successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<ProjectDashboardResponse> getProjectDashboard(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        ProjectDashboardResponse dashboard = ProjectDashboardResponse.builder()
                .projectId(project.getId())
                .projectCode(project.getProjectCode())
                .projectName(project.getProjectName())
                .sdlcStatus(project.getSdlcStatus().name())
                .progressPercentage(project.getProgressPercentage())
                .plannedManday(project.getBudgetManday())
                .usedManday(project.getUsedManday())
                .build();

        return ApiResponse.success(dashboard);
    }

    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .projectCode(project.getProjectCode())
                .projectName(project.getProjectName())
                .customerId(project.getCustomer().getId())
                .customerName(project.getCustomer().getCustomerName())
                .contractId(project.getContract() != null ? project.getContract().getId() : null)
                .contractNo(project.getContract() != null ? project.getContract().getContractNo() : null)
                .projectManager(project.getProjectManager())
                .businessAnalyst(project.getBusinessAnalyst())
                .systemAnalyst(project.getSystemAnalyst())
                .startDate(project.getStartDate())
                .plannedEndDate(project.getPlannedEndDate())
                .actualEndDate(project.getActualEndDate())
                .budgetManday(project.getBudgetManday())
                .usedManday(project.getUsedManday())
                .sdlcStatus(project.getSdlcStatus())
                .priority(project.getPriority())
                .progressPercentage(project.getProgressPercentage())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .phaseCount(project.getPhases() != null ? project.getPhases().size() : 0)
                .requirementCount(project.getRequirements() != null ? project.getRequirements().size() : 0)
                .build();
    }
}
