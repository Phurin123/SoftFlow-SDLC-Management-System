package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.TestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"testCase", "tester", "bug"})
@ToString(exclude = {"testCase", "tester", "bug"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Column(length = 4000)
    private String actualResult;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TestStatus testStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tester_id")
    private User tester;

    @Column
    private LocalDateTime testDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bug_id")
    private Bug bug;
}
