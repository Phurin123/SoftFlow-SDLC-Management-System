package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dfd_processes")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"dfd", "owner", "requirements", "dataFlowsIn", "dataFlowsOut"})
@ToString(exclude = {"dfd", "owner", "requirements", "dataFlowsIn", "dataFlowsOut"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DfdProcess extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String processCode;

    @Column(nullable = false, length = 100)
    private String processName;

    @Column(length = 4000)
    private String description;

    @Column(length = 500)
    private String inputData;

    @Column(length = 500)
    private String outputData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dfd_id", nullable = false)
    private Dfd dfd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(length = 20)
    private String status = "DRAFT";

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "dfd_process_requirements",
        joinColumns = @JoinColumn(name = "dfd_process_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    private List<Requirement> requirements = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sourceProcess", cascade = CascadeType.ALL)
    private List<DfdDataFlow> dataFlowsOut = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetProcess", cascade = CascadeType.ALL)
    private List<DfdDataFlow> dataFlowsIn = new ArrayList<>();
}
