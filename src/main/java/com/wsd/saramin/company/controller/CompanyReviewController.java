package com.wsd.saramin.company.controller;

import com.wsd.saramin.company.dto.CompanyReviewDTO;
import com.wsd.saramin.company.service.CompanyReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class CompanyReviewController {

    private final CompanyReviewService companyReviewService;

    public CompanyReviewController(CompanyReviewService companyReviewService) {
        this.companyReviewService = companyReviewService;
    }

    @PostMapping
    public ResponseEntity<String> addReview(
            @PathVariable Long companyId,
            @RequestParam Long userId,
            @RequestParam String content) {
        companyReviewService.addCompanyReview(userId, companyId, content);
        return ResponseEntity.ok("리뷰가 추가되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<CompanyReviewDTO>> getReviews(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyReviewService.getCompanyReviews(companyId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId,
            @RequestParam Long userId) {
        companyReviewService.deleteCompanyReview(userId, reviewId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
