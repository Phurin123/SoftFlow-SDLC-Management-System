package com.softflow.dto.request;

import com.softflow.enums.ApprovalStatus;
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
public class RequirementApprovalRequest {

    @NotNull(message = "Requirement is required")
    private UUID requirementId;

    @NotBlank(message = "Approver role is required")
    private String approverRole;

    private String approverName;

    @NotNull(message = "Approval status is required")
    private ApprovalStatus approvalStatus;

    private String comment;
}
