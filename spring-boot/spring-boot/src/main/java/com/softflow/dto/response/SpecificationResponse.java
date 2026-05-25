package com.softflow.dto.response;

import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.SpecificationStatus;
import com.softflow.enums.SpecificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationResponse {

    private UUID id;
    private UUID projectId;
    private String projectName;
    private String specCode;
    private String screenName;
    private SpecificationType specType;
    private String description;
    private String uiActions;
    private String validationRules;
    private String permissions;
    private BigDecimal estimatedManday;
    private String dependency;
    private ApprovalStatus headConfirmStatus;
    private ApprovalStatus customerConfirmStatus;
    private SpecificationStatus status;
    private Long versionSpecification;
    private List<RequirementRefResponse> relatedRequirements;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isFullyApproved;
}
