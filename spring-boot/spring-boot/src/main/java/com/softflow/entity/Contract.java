package com.softflow.entity;

import com.softflow.enums.ContractRenewalStatus;
import com.softflow.enums.ContractSignStatus;
import com.softflow.enums.ContractType;
import com.softflow.enums.PaymentTerms;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contract Entity - Manages contracts between customer and company.
 * Linked to Customer and Project.
 */
@Entity
@Table(name = "contracts")
@SQLDelete(sql = "UPDATE contracts SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Contract extends BaseEntity {

    @Column(name = "contract_no", nullable = false, unique = true, length = 50)
    private String contractNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = false, length = 30)
    private ContractType contractType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "contract_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal contractValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_terms", nullable = false, length = 20)
    private PaymentTerms paymentTerms;

    @Column(name = "scope_summary", length = 2000)
    private String scopeSummary;

    @Column(name = "contract_file_url", length = 500)
    private String contractFileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_status", nullable = false, length = 20)
    @Builder.Default
    private ContractSignStatus signStatus = ContractSignStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(name = "renewal_status", nullable = false, length = 20)
    @Builder.Default
    private ContractRenewalStatus renewalStatus = ContractRenewalStatus.NOT_RENEWED;

    @Column(name = "remark", length = 1000)
    private String remark;
}
