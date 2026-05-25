package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.RequirementPriority;
import com.softflow.entity.enums.RequirementStatus;
import com.softflow.entity.enums.RequirementType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requirements")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
    "project", "createdByUser", "approvals", "changeRequests",
    "dfdProcesses", "specifications", "testCases", "comments"
})
@ToString(exclude = {
    "project", "createdByUser", "approvals", "changeRequests",
    "dfdProcesses", "specifications", "testCases", "comments"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Requirement extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String requirementCode;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RequirementType requirementType;

    @Column(length = 50)
    private String source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequirementPriority priority = RequirementPriority.SHOULD;

    @Column(length = 500)
    private String businessValue;

    @Column(length = 2000)
    private String acceptanceCriteria;

    @Column(length = 500)
    private String relatedFiles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @Column(length = 20)
    private String baConfirmStatus = "PENDING";

    @Column(length = 20)
    private String customerConfirmStatus = "PENDING";

    @Column(length = 10)
    private String versionReq = "1.0";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequirementStatus status = RequirementStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder.Default
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequirementApproval> approvals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL)
    private List<RequirementChangeRequest> changeRequests = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "requirements")
    private List<DfdProcess> dfdProcesses = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "requirements")
    private List<Specification> specifications = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL)
    private List<TestCase> testCases = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
