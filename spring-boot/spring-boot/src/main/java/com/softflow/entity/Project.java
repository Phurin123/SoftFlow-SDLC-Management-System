package com.softflow.entity;

import com.softflow.enums.ProjectPriority;
import com.softflow.enums.SdlcStatus;
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
 * Project Entity - Core entity of the system.
 * Everything links to Project. Manages SDLC lifecycle status.
 */
@Entity
@Table(name = "projects")
@SQLDelete(sql = "UPDATE projects SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity {

    @Column(name = "project_code", nullable = false, unique = true, length = 50)
    private String projectCode;

    @Column(name = "project_name", nullable = false, length = 200)
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name = "project_manager", length = 100)
    private String projectManager;

    @Column(name = "business_analyst", length = 100)
    private String businessAnalyst;

    @Column(name = "system_analyst", length = 100)
    private String systemAnalyst;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "planned_end_date")
    private LocalDate plannedEndDate;

    @Column(name = "actual_end_date")
    private LocalDate actualEndDate;

    @Column(name = "budget_manday", precision = 10, scale = 2)
    private BigDecimal budgetManday;

    @Column(name = "used_manday", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal usedManday = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "sdlc_status", nullable = false, length = 30)
    @Builder.Default
    private SdlcStatus sdlcStatus = SdlcStatus.PROSPECT;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    @Builder.Default
    private ProjectPriority priority = ProjectPriority.MEDIUM;

    @Column(name = "progress_percentage")
    @Builder.Default
    private Integer progressPercentage = 0;

    @Column(name = "description", length = 2000)
    private String description;

    // Relationships
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @OrderBy("sequence")
    private List<ProjectPhase> phases = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Requirement> requirements = new ArrayList<>();
}
