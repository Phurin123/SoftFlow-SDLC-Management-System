package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "requirement_approvals")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"requirement", "approver"})
@ToString(exclude = {"requirement", "approver"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementApproval extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(length = 2000)
    private String comment;

    @Column
    private LocalDateTime approvalDate;

    @Column(length = 50)
    private String approvalType;
}
