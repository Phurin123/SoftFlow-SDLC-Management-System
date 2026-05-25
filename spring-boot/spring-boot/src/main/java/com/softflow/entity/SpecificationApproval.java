package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "specification_approvals")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"specification", "approver"})
@ToString(exclude = {"specification", "approver"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationApproval extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id", nullable = false)
    private Specification specification;

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
