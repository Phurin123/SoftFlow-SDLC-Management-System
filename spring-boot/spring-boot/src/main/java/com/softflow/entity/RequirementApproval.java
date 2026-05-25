package com.softflow.entity;

import com.softflow.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * RequirementApproval Entity - Tracks approval history for requirements.
 */
@Entity
@Table(name = "requirement_approvals")
@SQLDelete(sql = "UPDATE requirement_approvals SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RequirementApproval extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private Requirement requirement;

    @Column(name = "approver_role", nullable = false, length = 50)
    private String approverRole;

    @Column(name = "approver_name", length = 100)
    private String approverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 20)
    private ApprovalStatus approvalStatus;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
