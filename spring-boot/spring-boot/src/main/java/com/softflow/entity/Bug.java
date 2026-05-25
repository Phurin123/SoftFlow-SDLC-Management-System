package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.BugPriority;
import com.softflow.entity.enums.BugSeverity;
import com.softflow.entity.enums.BugStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bugs")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
    "foundBy", "assignedTo", "relatedTask", "relatedTestCase", "relatedSpec", "comments"
})
@ToString(exclude = {
    "foundBy", "assignedTo", "relatedTask", "relatedTestCase", "relatedSpec", "comments"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bug extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String bugCode;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BugSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BugPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "found_by_id")
    private User foundBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task relatedTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id")
    private TestCase relatedTestCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    private Specification relatedSpec;

    @Column
    private LocalDate foundDate;

    @Column
    private LocalDate fixDueDate;

    @Column
    private LocalDate fixedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BugStatus status = BugStatus.OPEN;

    @Column(length = 1000)
    private String retestPlan;

    @Column
    private LocalDate retestDate;

    @Builder.Default
    @OneToMany(mappedBy = "bug", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
