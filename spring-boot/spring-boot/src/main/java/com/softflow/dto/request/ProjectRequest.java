package com.softflow.dto.request;

import com.softflow.enums.ProjectPriority;
import com.softflow.enums.SdlcStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Project code is required")
    private String projectCode;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotNull(message = "Customer is required")
    private UUID customerId;
    private UUID contractId;
    private String projectManager;
    private String businessAnalyst;
    private String systemAnalyst;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private BigDecimal budgetManday;
    private ProjectPriority priority;
    private String description;
    private SdlcStatus sdlcStatus;
}
