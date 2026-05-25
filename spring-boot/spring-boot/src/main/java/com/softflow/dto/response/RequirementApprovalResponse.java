package com.softflow.dto.response;

import com.softflow.enums.ApprovalStatus;
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
public class RequirementApprovalResponse {

    private UUID id;
    private UUID requirementId;
    private String requirementCode;
    private String approverRole;
    private String approverName;
    private ApprovalStatus approvalStatus;
    private String comment;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
}
