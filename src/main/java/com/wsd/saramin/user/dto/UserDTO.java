package com.wsd.saramin.user.dto;

import com.wsd.saramin.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private long userId;
    private String email;
    private String name;
    private int age;
    private String phone;
    private String region;
    private User.Gender gender;
    private User.Role role;
    private LocalDateTime register;

    // Entity => DTO 변환 생성자
    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.region = user.getRegion();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.register = user.getRegister();
    }
}
