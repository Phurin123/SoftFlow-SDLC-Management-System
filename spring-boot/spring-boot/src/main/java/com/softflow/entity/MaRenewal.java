package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ma_renewals")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"contractMa", "requestedBy", "approvedBy"})
@ToString(exclude = {"contractMa", "requestedBy", "approvedBy"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaRenewal extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String renewalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_ma_id", nullable = false)
    private Contract contractMa;

    @Column(nullable = false)
    private LocalDate newStartDate;

    @Column(nullable = false)
    private LocalDate newEndDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal renewalValue;

    @Column(length = 500)
    private String proposalFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_id")
    private User requestedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Column(length = 2000)
    private String remark;
}
