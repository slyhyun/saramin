package com.wsd.saramin.job.controller;

import com.wsd.saramin.job.dto.JobReviewDTO;
import com.wsd.saramin.job.service.JobReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs/{jobId}/reviews")
public class JobReviewController {

    private final JobReviewService jobReviewService;

    public JobReviewController(JobReviewService jobReviewService) {
        this.jobReviewService = jobReviewService;
    }

    @PostMapping
    public ResponseEntity<String> addReview(
            @PathVariable Long jobId,
            @RequestParam Long userId,
            @RequestBody String content) {
        jobReviewService.addReview(jobId, userId, content);
        return ResponseEntity.ok("리뷰가 추가되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<JobReviewDTO>> getReviewsByJob(@PathVariable Long jobId) {
        List<JobReviewDTO> reviews = jobReviewService.getReviewsByJob(jobId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long jobId,
            @PathVariable Long reviewId,
            @RequestParam Long userId) {
        jobReviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
