package com.softflow.dto.request;

import com.softflow.enums.RequirementPriority;
import com.softflow.enums.RequirementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementRequest {

    @NotNull(message = "Project is required")
    private UUID projectId;

    @NotBlank(message = "Requirement code is required")
    private String requirementCode;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Requirement type is required")
    private RequirementType requirementType;

    private String source;

    @NotNull(message = "Priority is required")
    private RequirementPriority priority;

    private String businessValue;
    private String acceptanceCriteria;
    private String relatedFiles;
}
