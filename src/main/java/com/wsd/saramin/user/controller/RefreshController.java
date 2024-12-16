package com.wsd.saramin.user.controller;

import com.wsd.saramin.user.dto.RefreshDTO;
import com.wsd.saramin.user.service.RefreshService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RefreshController {

    private final RefreshService refreshService;

    public RefreshController(RefreshService refreshService) {
        this.refreshService = refreshService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshDTO> refreshAccessToken(@RequestBody RefreshDTO refreshDTO) {
        RefreshDTO updatedTokens = refreshService.refreshAccessToken(refreshDTO.getRefreshToken());
        return ResponseEntity.ok(updatedTokens);
    }
}
