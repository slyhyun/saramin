package com.wsd.saramin.bookmark.company.entity;

import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.company.entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_bookmark")
@Getter
@Setter
public class CompanyBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime date; // 생성일시 추가
}
