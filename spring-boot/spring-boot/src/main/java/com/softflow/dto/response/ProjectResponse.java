package com.softflow.dto.response;

import com.softflow.enums.ProjectPriority;
import com.softflow.enums.SdlcStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private UUID id;
    private String projectCode;
    private String projectName;
    private UUID customerId;
    private String customerName;
    private UUID contractId;
    private String contractNo;
    private String projectManager;
    private String businessAnalyst;
    private String systemAnalyst;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private LocalDate actualEndDate;
    private BigDecimal budgetManday;
    private BigDecimal usedManday;
    private SdlcStatus sdlcStatus;
    private ProjectPriority priority;
    private Integer progressPercentage;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer phaseCount;
    private Integer requirementCount;
}
