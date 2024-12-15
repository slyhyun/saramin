package com.wsd.saramin.company.controller;

import com.wsd.saramin.company.dto.CompanyReviewDTO;
import com.wsd.saramin.company.service.CompanyReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
@Tag(name = "Company Reviews", description = "회사 리뷰 관리 API")
public class CompanyReviewController {

    private final CompanyReviewService companyReviewService;

    public CompanyReviewController(CompanyReviewService companyReviewService) {
        this.companyReviewService = companyReviewService;
    }

    @Operation(
            summary = "리뷰 추가",
            description = "특정 회사에 리뷰를 추가합니다.",
            parameters = {
                    @Parameter(name = "companyId", description = "리뷰를 추가할 회사 ID", required = true),
                    @Parameter(name = "userId", description = "리뷰를 작성한 사용자 ID", required = true),
                    @Parameter(name = "content", description = "리뷰 내용", required = true)
            }
    )
    @PostMapping
    public ResponseEntity<String> addReview(
            @PathVariable Long companyId,
            @RequestParam Long userId,
            @RequestParam String content) {
        companyReviewService.addCompanyReview(userId, companyId, content);
        return ResponseEntity.ok("리뷰가 추가되었습니다.");
    }

    @Operation(
            summary = "리뷰 조회",
            description = "특정 회사의 모든 리뷰를 조회합니다.",
            parameters = @Parameter(name = "companyId", description = "리뷰를 조회할 회사 ID", required = true)
    )
    @GetMapping
    public ResponseEntity<List<CompanyReviewDTO>> getReviews(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyReviewService.getCompanyReviews(companyId));
    }

    @Operation(
            summary = "리뷰 삭제",
            description = "특정 회사의 특정 리뷰를 삭제합니다.",
            parameters = {
                    @Parameter(name = "companyId", description = "리뷰가 있는 회사 ID", required = true),
                    @Parameter(name = "reviewId", description = "삭제할 리뷰 ID", required = true),
                    @Parameter(name = "userId", description = "리뷰 삭제 요청 사용자 ID", required = true)
            }
    )
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId,
            @RequestParam Long userId) {
        companyReviewService.deleteCompanyReview(userId, reviewId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
