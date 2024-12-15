package com.wsd.saramin.user.controller;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.user.dto.ProfileDTO;
import com.wsd.saramin.user.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Profile Management", description = "프로필 관리 API")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "프로필 조회", description = "특정 사용자의 프로필 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "프로필 조회 성공",
            content = @Content(schema = @Schema(implementation = ProfileDTO.class)))
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@RequestParam Long userId) {
        ProfileDTO profile = profileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "프로필 수정", description = "특정 사용자의 프로필 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 잘못되었습니다.")
    })
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @RequestParam Long userId, @Valid @RequestBody ProfileDTO profileDTO) {
        profileService.updateProfile(userId, profileDTO);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

    @Operation(summary = "Job 북마크 삭제", description = "특정 사용자의 Job 북마크를 삭제합니다.")
    @DeleteMapping("/profile/bookmark/job/{jobBookmarkId}")
    public ResponseEntity<String> deleteJobBookmark(@RequestParam Long userId, @PathVariable Long jobBookmarkId) {
        profileService.deleteJobBookmark(userId, jobBookmarkId);
        return ResponseEntity.ok("Job 북마크가 삭제되었습니다.");
    }

    @Operation(summary = "Company 북마크 삭제", description = "특정 사용자의 Company 북마크를 삭제합니다.")
    @DeleteMapping("/profile/bookmark/company/{companyBookmarkId}")
    public ResponseEntity<String> deleteCompanyBookmark(@RequestParam Long userId, @PathVariable Long companyBookmarkId) {
        profileService.deleteCompanyBookmark(userId, companyBookmarkId);
        return ResponseEntity.ok("Company 북마크가 삭제되었습니다.");
    }

    @Operation(summary = "Job 북마크 조회", description = "특정 사용자의 Job 북마크 목록을 반환합니다.")
    @GetMapping("/profile/bookmark/jobs")
    public ResponseEntity<List<JobBookmarkDTO>> getJobBookmarks(@RequestParam Long userId) {
        List<JobBookmarkDTO> bookmarks = profileService.getJobBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }

    @Operation(summary = "Company 북마크 조회", description = "특정 사용자의 Company 북마크 목록을 반환합니다.")
    @GetMapping("/profile/bookmark/companies")
    public ResponseEntity<List<CompanyBookmarkDTO>> getCompanyBookmarks(@RequestParam Long userId) {
        List<CompanyBookmarkDTO> bookmarks = profileService.getCompanyBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }

    @Operation(summary = "Apply한 Job 목록 조회", description = "특정 사용자가 지원한 Job 목록을 반환합니다.")
    @GetMapping("/profile/applied-jobs")
    public ResponseEntity<List<ApplyDTO>> getAppliedJobs(@RequestParam Long userId) {
        List<ApplyDTO> appliedJobs = profileService.getAppliedJobs(userId);
        return ResponseEntity.ok(appliedJobs);
    }
}
