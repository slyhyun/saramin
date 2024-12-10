package com.wsd.saramin.user.service;

import com.wsd.saramin.user.dto.ProfileDTO;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updateProfile(Long userId, ProfileDTO profileDTO) {
        // 사용자 검색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 프로필 업데이트
        if (profileDTO.getName() != null) {
            user.setName(profileDTO.getName());
        }
        if (profileDTO.getPhone() != null) {
            user.setPhone(profileDTO.getPhone());
        }
        if (profileDTO.getRegion() != null) {
            user.setRegion(profileDTO.getRegion());
        }
        if (profileDTO.getAge() > 0) {
            user.setAge(profileDTO.getAge());
        }
        if (profileDTO.getPassword() != null && !profileDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
        }

        // 변경사항 저장
        userRepository.save(user);
    }
}
