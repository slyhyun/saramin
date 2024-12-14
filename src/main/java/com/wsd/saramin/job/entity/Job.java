package com.wsd.saramin.job.entity;

import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import com.wsd.saramin.company.entity.Company;
import com.wsd.saramin.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private long view = 0;

    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "company_name", nullable = false)
    private String companyName; // 직접 저장된 회사 이름

    @Column
    private String link; // 공고 링크

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> apply = new ArrayList<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobBookmark> jobBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobReview> jobReviews = new ArrayList<>();

    // 회사 이름을 설정하는 메서드
    @PrePersist
    @PreUpdate
    public void updateCompanyName() {
        if (this.company != null) {
            this.companyName = this.company.getName();
        }
    }
}
