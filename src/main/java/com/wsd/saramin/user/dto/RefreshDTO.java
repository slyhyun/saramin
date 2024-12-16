package com.wsd.saramin.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshDTO {
    private String refreshToken;
    private String accessToken;

    public RefreshDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
