package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"invoice"})
@ToString(exclude = {"invoice"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Column
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(length = 100)
    private String paymentMethod;

    @Column(length = 500)
    private String referenceNo;

    @Column(length = 500)
    private String receiptFile;

    @Column(length = 1000)
    private String remark;
}
