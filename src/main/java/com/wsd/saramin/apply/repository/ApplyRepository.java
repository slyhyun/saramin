package com.wsd.saramin.apply.repository;

import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.apply.entity.Apply.Status;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByUserAndJob(User user, Job job); // Job 엔티티를 직접 사용

    List<Apply> findAllByUser(User user); // 사용자별 지원 내역 조회
    List<Apply> findAllByJob(Job job); // 공고별 지원 내역 조회

    // 사용자별 최신순 정렬 및 페이지네이션
    Page<Apply> findAllByUser(User user, Pageable pageable);

    // 사용자와 상태별 최신순 정렬 및 페이지네이션
    Page<Apply> findAllByUserAndStatus(User user, Status status, Pageable pageable);
}
