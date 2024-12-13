package com.wsd.saramin.bookmark.job.controller;

import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.bookmark.job.service.JobBookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks/jobs")
public class JobBookmarkController {

    private final JobBookmarkService jobBookmarkService;

    public JobBookmarkController(JobBookmarkService jobBookmarkService) {
        this.jobBookmarkService = jobBookmarkService;
    }

    @PostMapping("/{jobId}")
    public ResponseEntity<String> toggleJobBookmark(@RequestParam Long userId, @PathVariable Long jobId) {
        jobBookmarkService.toggleJobBookmark(userId, jobId);
        return ResponseEntity.ok("북마크 상태가 변경되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<JobBookmarkDTO>> getJobBookmarks(@RequestParam Long userId) {
        return ResponseEntity.ok(jobBookmarkService.getJobBookmarks(userId));
    }
}
