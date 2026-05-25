package com.softflow.entity;

import com.softflow.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"projects", "contracts", "invoices", "maTickets"})
@ToString(exclude = {"projects", "contracts", "invoices", "maTickets"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String customerName;

    @Column(length = 20)
    private String taxId;

    @Column(length = 500)
    private String address;

    @Column(length = 100)
    private String contactPerson;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String lineId;

    @Column(length = 50)
    private String customerType;

    @Column(length = 500)
    private String remark;

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<MaTicket> maTickets = new ArrayList<>();
}
