package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dfds")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"project", "processes", "dataFlows"})
@ToString(exclude = {"project", "processes", "dataFlows"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dfd extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String dfdCode;

    @Column(nullable = false, length = 100)
    private String dfdName;

    @Column(length = 20)
    private String dfdLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(length = 20)
    private String status = "DRAFT";

    @Builder.Default
    @OneToMany(mappedBy = "dfd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DfdProcess> processes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "dfd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DfdDataFlow> dataFlows = new ArrayList<>();
}
