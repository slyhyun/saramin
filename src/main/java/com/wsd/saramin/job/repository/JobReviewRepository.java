package com.wsd.saramin.job.repository;

import com.wsd.saramin.job.entity.JobReview;
import com.wsd.saramin.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobReviewRepository extends JpaRepository<JobReview, Long> {
    List<JobReview> findByJob(Job job);
}
