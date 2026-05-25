package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ProjectPriority;
import com.softflow.entity.enums.SdlcStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
    "customer", "contract", "projectManager", "ba", "sa",
    "phases", "requirements", "contracts", "erDiagrams",
    "specifications", "tasks", "testPlans", "deliveries", "maTickets"
})
@ToString(exclude = {
    "customer", "contract", "projectManager", "ba", "sa",
    "phases", "requirements", "contracts", "erDiagrams",
    "specifications", "tasks", "testPlans", "deliveries", "maTickets"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String projectCode;

    @Column(nullable = false, length = 200)
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id")
    private User projectManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ba_id")
    private User ba;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sa_id")
    private User sa;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate plannedEndDate;

    @Column
    private LocalDate actualEndDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal budgetManday;

    @Column(precision = 10, scale = 2)
    private BigDecimal usedManday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SdlcStatus projectStatus = SdlcStatus.PROSPECT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectPriority priority = ProjectPriority.MEDIUM;

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectPhase> phases = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Requirement> requirements = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ErDiagram> erDiagrams = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Specification> specifications = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TestPlan> testPlans = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Delivery> deliveries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<MaTicket> maTickets = new ArrayList<>();
}
