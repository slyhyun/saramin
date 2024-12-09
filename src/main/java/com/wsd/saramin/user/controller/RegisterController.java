package com.wsd.saramin.user.controller;

import com.wsd.saramin.user.dto.RegisterDTO;
import com.wsd.saramin.user.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    // 회원 가입 요청 처리
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        // 회원 가입 서비스 호출
        registerService.register(registerDTO);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }
}
