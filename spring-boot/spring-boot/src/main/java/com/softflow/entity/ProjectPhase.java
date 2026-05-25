package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.PhaseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project_phases")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"project", "owner", "milestones", "nextPhase"})
@ToString(exclude = {"project", "owner", "milestones", "nextPhase"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPhase extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 100)
    private String phaseName;

    @Column(length = 1000)
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PhaseStatus status = PhaseStatus.NOT_STARTED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependency_phase_id")
    private ProjectPhase nextPhase;

    @Column(precision = 5, scale = 2)
    private Double progress = 0.0;

    @Builder.Default
    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Milestone> milestones = new ArrayList<>();
}
