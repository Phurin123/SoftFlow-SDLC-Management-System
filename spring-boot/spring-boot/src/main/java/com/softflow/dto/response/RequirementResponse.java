package com.softflow.dto.response;

import com.softflow.enums.ApprovalStatus;
import com.softflow.enums.RequirementPriority;
import com.softflow.enums.RequirementStatus;
import com.softflow.enums.RequirementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementResponse {

    private UUID id;
    private UUID projectId;
    private String projectName;
    private String requirementCode;
    private String title;
    private String description;
    private RequirementType requirementType;
    private String source;
    private RequirementPriority priority;
    private String businessValue;
    private String acceptanceCriteria;
    private String relatedFiles;
    private ApprovalStatus baConfirmStatus;
    private ApprovalStatus customerConfirmStatus;
    private String versionRequirement;
    private RequirementStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private Boolean isFullyApproved;
}
