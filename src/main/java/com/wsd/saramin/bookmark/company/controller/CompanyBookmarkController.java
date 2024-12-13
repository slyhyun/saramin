package com.wsd.saramin.bookmark.company.controller;

import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.company.service.CompanyBookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks/company")
public class CompanyBookmarkController {

    private final CompanyBookmarkService companyBookmarkService;

    public CompanyBookmarkController(CompanyBookmarkService companyBookmarkService) {
        this.companyBookmarkService = companyBookmarkService;
    }

    @PostMapping
    public ResponseEntity<String> toggleBookmark(@RequestParam Long userId, @RequestParam Long companyId) {
        companyBookmarkService.toggleBookmark(userId, companyId);
        return ResponseEntity.ok("북마크 상태가 변경되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<CompanyBookmarkDTO>> getBookmarks(@RequestParam Long userId) {
        List<CompanyBookmarkDTO> bookmarks = companyBookmarkService.getBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }
}
