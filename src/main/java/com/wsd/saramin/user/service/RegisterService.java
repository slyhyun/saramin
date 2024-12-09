package com.wsd.saramin.user.service;

import com.wsd.saramin.user.dto.RegisterDTO;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterDTO registerDTO) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        // User 엔티티 생성 및 필드 설정
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); // 비밀번호 암호화
        user.setName(registerDTO.getName());
        user.setPhone(registerDTO.getPhone());
        user.setRegion(registerDTO.getRegion());
        user.setAge(registerDTO.getAge());
        user.setGender(registerDTO.getGender());
        user.setRole(User.Role.MEMBER);

        // 데이터베이스에 사용자 저장
        userRepository.save(user);
    }
}
