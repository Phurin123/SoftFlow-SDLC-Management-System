package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "project_members")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"project", "user"})
@ToString(exclude = {"project", "user"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String roleInProject;

    @Column(length = 500)
    private String responsibility;

    @Column
    private LocalDate joinDate;

    @Column
    private LocalDate leaveDate;
}
