package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_cases")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"testPlan", "requirement", "relatedTask", "specification", "testResults"})
@ToString(exclude = {"testPlan", "requirement", "relatedTask", "specification", "testResults"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCase extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String testCaseCode;

    @Column(nullable = false, length = 200)
    private String module;

    @Column(nullable = false, length = 200)
    private String scenario;

    @Column(length = 4000)
    private String testSteps;

    @Column(length = 1000)
    private String expectedResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_plan_id", nullable = false)
    private TestPlan testPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task relatedTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    private Specification specification;

    @Builder.Default
    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL)
    private List<TestResult> testResults = new ArrayList<>();
}
