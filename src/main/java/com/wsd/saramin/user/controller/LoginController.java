package com.wsd.saramin.user.controller;

import com.wsd.saramin.user.dto.LoginDTO;
import com.wsd.saramin.user.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "사용자 인증 API")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(
            summary = "사용자 로그인",
            description = "유효한 이메일과 비밀번호를 사용하여 JWT 토큰을 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 정보",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공, JWT 토큰 반환"),
                    @ApiResponse(responseCode = "401", description = "로그인 실패, 인증 오류"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        // LoginService를 호출하여 JWT 토큰 생성
        String token = loginService.login(loginDTO);
        return ResponseEntity.ok(token);
    }
}
