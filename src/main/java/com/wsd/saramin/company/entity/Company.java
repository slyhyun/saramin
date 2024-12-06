package com.wsd.saramin.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "company")
@Entity
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private long companyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int employee;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = false)
    private String ceo;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String establishment;

    @Column(nullable = false)
    private long revenue;

    @Column(nullable = false)
    private long salary;
}
