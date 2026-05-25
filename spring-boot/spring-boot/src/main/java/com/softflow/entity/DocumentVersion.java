package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.ApprovalStatus;
import com.softflow.entity.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_versions")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"changedBy"})
@ToString(exclude = {"changedBy"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersion extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DocumentType documentType;

    @Column(nullable = false, length = 50)
    private String documentCode;

    @Column(nullable = false, length = 20)
    private String versionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by_id")
    private User changedBy;

    @Column
    private LocalDateTime changedDate;

    @Column(length = 1000)
    private String changeSummary;

    @Column(length = 20)
    private String previousVersion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column
    private Boolean isActiveVersion = false;

    @Column(length = 500)
    private String filePath;
}
