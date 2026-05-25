package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_plans")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"project", "testCases"})
@ToString(exclude = {"project", "testCases"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPlan extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String planCode;

    @Column(nullable = false, length = 200)
    private String planName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(length = 50)
    private String testType;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String status;

    @Builder.Default
    @OneToMany(mappedBy = "testPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestCase> testCases = new ArrayList<>();
}
