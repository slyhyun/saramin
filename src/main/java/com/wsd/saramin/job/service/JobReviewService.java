package com.wsd.saramin.job.service;

import com.wsd.saramin.job.dto.JobReviewDTO;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.entity.JobReview;
import com.wsd.saramin.job.repository.JobRepository;
import com.wsd.saramin.job.repository.JobReviewRepository;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobReviewService {

    private final JobReviewRepository jobReviewRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobReviewService(JobReviewRepository jobReviewRepository, JobRepository jobRepository, UserRepository userRepository) {
        this.jobReviewRepository = jobReviewRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addReview(Long jobId, Long userId, String content) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용 공고를 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        JobReview review = new JobReview();
        review.setJob(job);
        review.setUser(user);
        review.setContent(content);

        jobReviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<JobReviewDTO> getReviewsByJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용 공고를 찾을 수 없습니다."));

        return jobReviewRepository.findByJob(job).stream()
                .map(JobReviewDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        JobReview review = jobReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser().getUserId() != userId) {
            throw new IllegalArgumentException("리뷰를 삭제할 권한이 없습니다.");
        }

        jobReviewRepository.delete(review);
    }
}
