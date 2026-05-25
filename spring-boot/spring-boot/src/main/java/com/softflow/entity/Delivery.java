package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deliveries")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"project", "approvedBy", "userManuals", "invoices"})
@ToString(exclude = {"project", "approvedBy", "userManuals", "invoices"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String deliveryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(length = 200)
    private String deliveryLetter;

    @Column(length = 500)
    private String deliveryChecklist;

    @Column(length = 500)
    private String releaseNote;

    @Column(length = 500)
    private String testSummaryReport;

    @Column(length = 500)
    private String uatSignOff;

    @Column(length = 500)
    private String sourceCodePackage;

    @Column(length = 500)
    private String databaseScript;

    @Column(length = 500)
    private String credentialSheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @Column(length = 20)
    private String status = "PENDING";

    @Builder.Default
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<UserManual> userManuals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();
}
