package com.softflow.dto.response;

import com.softflow.enums.PhaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPhaseResponse {

    private UUID id;
    private UUID projectId;
    private String projectName;
    private String phaseName;
    private String description;
    private Integer sequence;
    private LocalDate startDate;
    private LocalDate endDate;
    private String owner;
    private PhaseStatus status;
    private Integer progressPercentage;
    private UUID dependsOnPhaseId;
    private String dependsOnPhaseName;
    private LocalDateTime createdAt;
    private Integer milestoneCount;
}
