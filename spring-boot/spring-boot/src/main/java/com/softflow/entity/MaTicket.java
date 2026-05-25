package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import com.softflow.entity.enums.TicketStatus;
import com.softflow.entity.enums.TicketType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ma_tickets")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"customer", "project", "contractMa", "assignedTo", "comments"})
@ToString(exclude = {"customer", "project", "contractMa", "assignedTo", "comments"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaTicket extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String ticketNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_ma_id")
    private Contract contractMa;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketType ticketType;

    @Column(length = 20)
    private String severity;

    @Column(length = 50)
    private String sla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketStatus status = TicketStatus.OPEN;

    @Column
    private LocalDate reportedDate;

    @Column
    private LocalDate dueDate;

    @Column
    private LocalDate resolvedDate;

    @Builder.Default
    @OneToMany(mappedBy = "maTicket", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
