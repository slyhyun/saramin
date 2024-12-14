package com.wsd.saramin.job.entity;

import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.company.entity.Company;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> apply = new ArrayList<>(); // 채용 공고에 대한 지원 내역

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobBookmark> jobBookmarks = new ArrayList<>(); // Job 북마크

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobReview> jobReviews = new ArrayList<>(); // Job 리뷰
}
