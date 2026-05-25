package com.softflow.dto.request;

import com.softflow.enums.PhaseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPhaseRequest {

    @NotNull(message = "Project is required")
    private UUID projectId;

    @NotBlank(message = "Phase name is required")
    private String phaseName;

    private String description;

    @NotNull(message = "Sequence is required")
    private Integer sequence;

    private LocalDate startDate;
    private LocalDate endDate;
    private String owner;
    private PhaseStatus status;
    private UUID dependsOnPhaseId;
}
