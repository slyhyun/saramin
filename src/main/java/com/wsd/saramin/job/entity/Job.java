package com.wsd.saramin.job.entity;

import com.wsd.saramin.company.entity.Company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Table(name = "job")
@Entity
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private long jobId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String experience;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String education;

    @Column(nullable = false)
    private String deadline;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false)
    private long view;

    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
