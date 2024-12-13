package com.wsd.saramin.user.controller;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.user.dto.ProfileDTO;
import com.wsd.saramin.user.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@RequestParam Long userId) {
        ProfileDTO profile = profileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    // 프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @RequestParam Long userId, @Valid @RequestBody ProfileDTO profileDTO) {
        profileService.updateProfile(userId, profileDTO);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

    // Job 북마크 삭제
    @DeleteMapping("/profile/bookmark/job/{jobBookmarkId}")
    public ResponseEntity<String> deleteJobBookmark(@RequestParam Long userId, @PathVariable Long jobBookmarkId) {
        profileService.deleteJobBookmark(userId, jobBookmarkId);
        return ResponseEntity.ok("Job 북마크가 삭제되었습니다.");
    }

    // Company 북마크 삭제
    @DeleteMapping("/profile/bookmark/company/{companyBookmarkId}")
    public ResponseEntity<String> deleteCompanyBookmark(@RequestParam Long userId, @PathVariable Long companyBookmarkId) {
        profileService.deleteCompanyBookmark(userId, companyBookmarkId);
        return ResponseEntity.ok("Company 북마크가 삭제되었습니다.");
    }

    // Job 북마크 조회
    @GetMapping("/profile/bookmark/jobs")
    public ResponseEntity<List<JobBookmarkDTO>> getJobBookmarks(@RequestParam Long userId) {
        List<JobBookmarkDTO> bookmarks = profileService.getJobBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }

    // Company 북마크 조회
    @GetMapping("/profile/bookmark/companies")
    public ResponseEntity<List<CompanyBookmarkDTO>> getCompanyBookmarks(@RequestParam Long userId) {
        List<CompanyBookmarkDTO> bookmarks = profileService.getCompanyBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }

    // Apply한 Job 목록 조회
    @GetMapping("/profile/applied-jobs")
    public ResponseEntity<List<ApplyDTO>> getAppliedJobs(@RequestParam Long userId) {
        List<ApplyDTO> appliedJobs = profileService.getAppliedJobs(userId);
        return ResponseEntity.ok(appliedJobs);
    }
}
