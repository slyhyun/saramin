package com.wsd.saramin.bookmark.company.controller;

import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.company.service.CompanyBookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks/company")
@Tag(name = "Company Bookmark", description = "회사 북마크 API")
public class CompanyBookmarkController {

    private final CompanyBookmarkService companyBookmarkService;

    public CompanyBookmarkController(CompanyBookmarkService companyBookmarkService) {
        this.companyBookmarkService = companyBookmarkService;
    }

    @Operation(summary = "회사 북마크 추가/삭제", description = "사용자가 특정 회사 북마크 상태를 토글합니다.")
    @PostMapping
    public ResponseEntity<String> toggleBookmark(
            @Parameter(description = "사용자 ID") @RequestParam Long userId,
            @Parameter(description = "회사 ID") @RequestParam Long companyId) {
        companyBookmarkService.toggleBookmark(userId, companyId);
        return ResponseEntity.ok("북마크 상태가 변경되었습니다.");
    }

    @Operation(summary = "사용자의 회사 북마크 조회", description = "특정 사용자의 모든 회사 북마크를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CompanyBookmarkDTO>> getBookmarks(
            @Parameter(description = "사용자 ID") @RequestParam Long userId) {
        List<CompanyBookmarkDTO> bookmarks = companyBookmarkService.getBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }
}
