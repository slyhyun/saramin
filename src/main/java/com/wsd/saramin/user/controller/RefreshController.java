package com.wsd.saramin.user.controller;

import com.wsd.saramin.user.dto.RefreshDTO;
import com.wsd.saramin.user.service.RefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Token Management", description = "토큰 관리 API") // API 그룹 태그 추가
public class RefreshController {

    private final RefreshService refreshService;

    public RefreshController(RefreshService refreshService) {
        this.refreshService = refreshService;
    }

    @Operation(
            summary = "토큰 갱신",
            description = "유효한 Refresh 토큰을 사용하여 새로운 Access 및 Refresh 토큰을 발급받습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공",
                    content = @Content(schema = @Schema(implementation = RefreshDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(type = "string")))
    })
    @PostMapping("/refresh")
    public ResponseEntity<RefreshDTO> refreshAccessToken(@RequestBody RefreshDTO refreshDTO) {
        RefreshDTO updatedTokens = refreshService.refreshAccessToken(refreshDTO.getRefreshToken());
        return ResponseEntity.ok(updatedTokens);
    }
}
