package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specifications")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
    "project", "requirements", "erTables", "erColumns", "tasks", "approvals", "comments"
})
@ToString(exclude = {
    "project", "requirements", "erTables", "erColumns", "tasks", "approvals", "comments"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specification extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String specCode;

    @Column(nullable = false, length = 200)
    private String specName;

    @Column(length = 50)
    private String specType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String uiActions;

    @Column(length = 1000)
    private String validationRule;

    @Column(length = 200)
    private String permission;

    @Column(precision = 10, scale = 2)
    private BigDecimal estimatedManday;

    @Column(length = 500)
    private String dependency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "spec_requirements",
        joinColumns = @JoinColumn(name = "spec_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    private List<Requirement> requirements = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "spec_er_tables",
        joinColumns = @JoinColumn(name = "spec_id"),
        inverseJoinColumns = @JoinColumn(name = "er_table_id")
    )
    private List<ErTable> erTables = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "spec_er_columns",
        joinColumns = @JoinColumn(name = "spec_id"),
        inverseJoinColumns = @JoinColumn(name = "er_column_id")
    )
    private List<ErColumn> erColumns = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specification", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specification", cascade = CascadeType.ALL)
    private List<SpecificationApproval> approvals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specification", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
