package com.softflow.dto.request;

import com.softflow.enums.SpecificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationRequest {

    @NotNull(message = "Project is required")
    private UUID projectId;

    @NotBlank(message = "Spec code is required")
    private String specCode;

    @NotBlank(message = "Screen name is required")
    private String screenName;

    @NotNull(message = "Specification type is required")
    private SpecificationType specType;

    private String description;
    private String uiActions;
    private String validationRules;
    private String permissions;
    private BigDecimal estimatedManday;
    private String dependency;
    private List<UUID> relatedRequirementIds;
}
