package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_dependencies")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"task", "dependsOnTask"})
@ToString(exclude = {"task", "dependsOnTask"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDependency extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depends_on_task_id", nullable = false)
    private Task dependsOnTask;

    @Column(length = 50)
    private String dependencyType;
}
