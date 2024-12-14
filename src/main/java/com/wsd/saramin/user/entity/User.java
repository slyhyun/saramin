package com.wsd.saramin.user.entity;

import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.bookmark.company.entity.CompanyBookmark;
import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.entity.JobReview;
import com.wsd.saramin.company.entity.CompanyReview;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "`user`")
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String region;

    @CreationTimestamp
    private LocalDateTime register;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> job = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> apply = new ArrayList<>(); // 사용자 지원 내역

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobBookmark> jobBookmarks = new ArrayList<>(); // Job 북마크

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBookmark> companyBookmarks = new ArrayList<>(); // Company 북마크

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobReview> jobReviews = new ArrayList<>(); // Job 리뷰 추가

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyReview> companyReviews = new ArrayList<>(); // Company 리뷰 추가

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Role {
        MEMBER,
        ADMIN
    }
}
