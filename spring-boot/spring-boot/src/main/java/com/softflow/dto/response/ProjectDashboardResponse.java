package com.softflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDashboardResponse {

    private UUID projectId;
    private String projectCode;
    private String projectName;
    private String sdlcStatus;
    private Integer progressPercentage;
    private String currentPhase;
    private Integer overdueTasks;
    private Integer pendingRequirements;
    private Integer openBugs;
    private BigDecimal plannedManday;
    private BigDecimal usedManday;
    private String invoiceStatus;
    private String maStatus;
}
