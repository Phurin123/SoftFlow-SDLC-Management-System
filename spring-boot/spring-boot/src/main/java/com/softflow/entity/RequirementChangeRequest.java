package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requirement_change_requests")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"requirement", "requestedBy", "approvals"})
@ToString(exclude = {"requirement", "requestedBy", "approvals"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementChangeRequest extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String changeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_id")
    private User requestedBy;

    @Column(length = 4000)
    private String description;

    @Column(length = 1000)
    private String dfdImpact;

    @Column(length = 1000)
    private String erImpact;

    @Column(length = 1000)
    private String uiImpact;

    @Column(length = 1000)
    private String apiImpact;

    @Column(length = 1000)
    private String testImpact;

    @Column(precision = 10, scale = 2)
    private BigDecimal mandayImpact;

    @Column
    private Integer timelineImpactDays;

    @Column(precision = 15, scale = 2)
    private BigDecimal costImpact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Builder.Default
    @OneToMany(mappedBy = "changeRequest", cascade = CascadeType.ALL)
    private List<RequirementApproval> approvals = new ArrayList<>();
}
