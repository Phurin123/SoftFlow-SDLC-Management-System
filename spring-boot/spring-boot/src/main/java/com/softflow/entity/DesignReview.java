package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import com.softflow.entity.enums.ReviewCommentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "design_reviews")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"reviewer", "assignedTo"})
@ToString(exclude = {"reviewer", "assignedTo"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignReview extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String reviewCode;

    @Column(nullable = false, length = 50)
    private String documentType;

    @Column(nullable = false, length = 50)
    private String documentCode;

    @Column(length = 200)
    private String itemName;

    @Column(length = 4000)
    private String issueDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReviewCommentType commentType;

    @Column(length = 20)
    private String severity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @Column
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Column(length = 2000)
    private String resolution;
}
