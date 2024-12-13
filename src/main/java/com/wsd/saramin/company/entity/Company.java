package com.wsd.saramin.company.entity;

import com.wsd.saramin.bookmark.company.entity.CompanyBookmark;
import com.wsd.saramin.job.entity.Job;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBookmark> companyBookmarks = new ArrayList<>(); // Company 북마크
}
