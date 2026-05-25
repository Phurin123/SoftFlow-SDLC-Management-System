package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "milestones")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"phase", "tasks"})
@ToString(exclude = {"phase", "tasks"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Milestone extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id", nullable = false)
    private ProjectPhase phase;

    @Column(nullable = false, length = 100)
    private String milestoneName;

    @Column(length = 1000)
    private String description;

    @Column
    private LocalDate plannedDate;

    @Column
    private LocalDate actualDate;

    @Column(length = 50)
    private String deliverable;

    @Column
    private Boolean isCompleted = false;

    @Builder.Default
    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
}
