package com.wsd.saramin.user.controller;

import com.wsd.saramin.user.dto.LoginDTO;
import com.wsd.saramin.user.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        // LoginService를 호출하여 JWT 토큰 생성
        String token = loginService.login(loginDTO);
        return ResponseEntity.ok(token);
    }
}
