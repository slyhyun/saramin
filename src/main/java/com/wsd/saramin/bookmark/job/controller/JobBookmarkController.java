package com.wsd.saramin.bookmark.job.controller;

import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.bookmark.job.service.JobBookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks/jobs")
@Tag(name = "Job Bookmark", description = "Job 북마크 관련 API")
public class JobBookmarkController {

    private final JobBookmarkService jobBookmarkService;

    public JobBookmarkController(JobBookmarkService jobBookmarkService) {
        this.jobBookmarkService = jobBookmarkService;
    }

    @Operation(summary = "Job 북마크 추가/삭제", description = "특정 사용자와 채용 공고의 북마크 상태를 토글합니다.")
    @PostMapping("/{jobId}")
    public ResponseEntity<String> toggleJobBookmark(
            @Parameter(description = "사용자 ID") @RequestParam Long userId,
            @Parameter(description = "채용 공고 ID") @PathVariable Long jobId) {
        jobBookmarkService.toggleJobBookmark(userId, jobId);
        return ResponseEntity.ok("북마크 상태가 변경되었습니다.");
    }

    @Operation(summary = "사용자의 Job 북마크 조회", description = "특정 사용자의 Job 북마크 목록을 페이지네이션 및 정렬 기준으로 조회합니다.")
    @GetMapping
    public ResponseEntity<List<JobBookmarkDTO>> getJobBookmarks(
            @Parameter(description = "사용자 ID") @RequestParam Long userId,
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "1") int page) {
        List<JobBookmarkDTO> bookmarks = jobBookmarkService.getJobBookmarks(userId, page);
        return ResponseEntity.ok(bookmarks);
    }
}
