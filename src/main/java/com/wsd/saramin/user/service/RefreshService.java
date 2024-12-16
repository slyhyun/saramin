package com.wsd.saramin.user.service;

import com.wsd.saramin.user.dto.RefreshDTO;
import com.wsd.saramin.config.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class RefreshService {

    private final JwtTokenProvider jwtTokenProvider;

    public RefreshService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public RefreshDTO refreshAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh 토큰이 유효하지 않습니다.");
        }

        String email = jwtTokenProvider.getEmail(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        String newAccessToken = jwtTokenProvider.createToken(email, role);

        return new RefreshDTO(newAccessToken, refreshToken);
    }
}
