package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
    "project", "specification", "milestone", "assignedTo",
    "dependencies", "dependentOn", "comments", "bugs", "testCases"
})
@ToString(exclude = {
    "project", "specification", "milestone", "assignedTo",
    "dependencies", "dependentOn", "comments", "bugs", "testCases"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String taskCode;

    @Column(nullable = false, length = 200)
    private String taskName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    private Specification specification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private LocalDate actualStart;

    @Column
    private LocalDate actualEnd;

    @Column(precision = 10, scale = 2)
    private BigDecimal estimateManday;

    @Column(precision = 10, scale = 2)
    private BigDecimal actualManday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status = TaskStatus.TODO;

    @Column(length = 1000)
    private String impactIfDelay;

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskDependency> dependencies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "dependsOnTask", cascade = CascadeType.ALL)
    private List<TaskDependency> dependentOn = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "relatedTask", cascade = CascadeType.ALL)
    private List<Bug> bugs = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "relatedTask", cascade = CascadeType.ALL)
    private List<TestCase> testCases = new ArrayList<>();
}
