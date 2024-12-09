package com.wsd.saramin.user.dto;

import com.wsd.saramin.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotNull(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "연락처는 필수입니다.")
    private String phone;

    @NotNull(message = "지역은 필수입니다.")
    private String region;

    @NotNull(message = "나이는 필수입니다.")
    private int age;

    @NotNull(message = "성별은 필수입니다.")
    private User.Gender gender;
}
