package com.wsd.saramin.apply.repository;

import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByUserAndJob(User user, Job job); // Job 엔티티를 직접 사용

    List<Apply> findAllByUser(User user); // 사용자별 지원 내역 조회
}
