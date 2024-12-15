package com.wsd.saramin.job.controller;

import com.wsd.saramin.job.dto.JobReviewDTO;
import com.wsd.saramin.job.service.JobReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs/{jobId}/reviews")
@Tag(name = "Job Review Management", description = "채용 공고 리뷰 관리 API")
public class JobReviewController {

    private final JobReviewService jobReviewService;

    public JobReviewController(JobReviewService jobReviewService) {
        this.jobReviewService = jobReviewService;
    }

    @Operation(
            summary = "채용 공고 리뷰 추가",
            description = "특정 채용 공고에 리뷰를 추가합니다.",
            parameters = {
                    @Parameter(name = "jobId", description = "채용 공고 ID", required = true),
                    @Parameter(name = "userId", description = "리뷰 작성자 ID", required = true)
            }
    )
    @PostMapping
    public ResponseEntity<String> addReview(
            @PathVariable Long jobId,
            @RequestParam Long userId,
            @RequestBody String content) {
        jobReviewService.addReview(jobId, userId, content);
        return ResponseEntity.ok("리뷰가 추가되었습니다.");
    }

    @Operation(
            summary = "채용 공고 리뷰 조회",
            description = "특정 채용 공고의 리뷰를 조회합니다.",
            parameters = @Parameter(name = "jobId", description = "채용 공고 ID", required = true)
    )
    @GetMapping
    public ResponseEntity<List<JobReviewDTO>> getReviewsByJob(@PathVariable Long jobId) {
        List<JobReviewDTO> reviews = jobReviewService.getReviewsByJob(jobId);
        return ResponseEntity.ok(reviews);
    }

    @Operation(
            summary = "채용 공고 리뷰 삭제",
            description = "특정 채용 공고의 리뷰를 삭제합니다.",
            parameters = {
                    @Parameter(name = "jobId", description = "채용 공고 ID", required = true),
                    @Parameter(name = "reviewId", description = "리뷰 ID", required = true),
                    @Parameter(name = "userId", description = "리뷰 작성자 ID", required = true)
            }
    )
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long jobId,
            @PathVariable Long reviewId,
            @RequestParam Long userId) {
        jobReviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
