package com.wsd.saramin.user.controller;

import com.wsd.saramin.user.dto.ProfileDTO;
import com.wsd.saramin.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
