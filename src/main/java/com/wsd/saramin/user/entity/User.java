package com.wsd.saramin.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "`user`")
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String region;

    @CreationTimestamp
    private LocalDateTime register;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Role {
        MEMBER,
        ADMIN
    }
}
