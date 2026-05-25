package com.softflow.entity;

import com.softflow.enums.PhaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectPhase Entity - Represents a phase in the project lifecycle.
 * Phases have dependencies on each other.
 */
@Entity
@Table(name = "project_phases")
@SQLDelete(sql = "UPDATE project_phases SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectPhase extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "phase_name", nullable = false, length = 100)
    private String phaseName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "owner", length = 100)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private PhaseStatus status = PhaseStatus.NOT_STARTED;

    @Column(name = "progress_percentage")
    @Builder.Default
    private Integer progressPercentage = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depends_on_phase_id")
    private ProjectPhase dependsOnPhase;

    // One Phase has many Milestones
    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @OrderBy("sequence")
    private List<Milestone> milestones = new ArrayList<>();
}
