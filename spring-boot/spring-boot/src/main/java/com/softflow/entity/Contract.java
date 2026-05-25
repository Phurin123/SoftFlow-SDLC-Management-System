package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ContractStatus;
import com.softflow.entity.enums.ContractType;
import com.softflow.entity.enums.RenewalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contracts")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"customer", "project", "invoices", "maRenewals"})
@ToString(exclude = {"customer", "project", "invoices", "maRenewals"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String contractNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ContractType contractType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal contractValue;

    @Column(length = 500)
    private String paymentTerms;

    @Column(length = 1000)
    private String scopeSummary;

    @Column(length = 500)
    private String contractFile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractStatus signStatus = ContractStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RenewalStatus renewalStatus = RenewalStatus.NOT_RENEWED;

    @Builder.Default
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "contractMa", cascade = CascadeType.ALL)
    private List<MaRenewal> maRenewals = new ArrayList<>();
}
