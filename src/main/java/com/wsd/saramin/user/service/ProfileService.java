package com.wsd.saramin.user.service;

import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.user.dto.ProfileDTO;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 프로필 조회
    @Transactional(readOnly = true)
    public ProfileDTO getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(user.getEmail());
        profileDTO.setName(user.getName());
        profileDTO.setPhone(user.getPhone());
        profileDTO.setRegion(user.getRegion());
        profileDTO.setAge(user.getAge());
        profileDTO.setGender(user.getGender());
        profileDTO.setAppliedJobs(
                user.getJob().stream()
                        .map(JobDTO::new) // Job -> JobDTO 변환
                        .collect(Collectors.toList())
        );

        return profileDTO;
    }

    // 사용자 프로필 수정
    @Transactional
    public ProfileDTO updateProfile(Long userId, ProfileDTO profileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        updateUserFields(user, profileDTO);
        userRepository.save(user);

        // 수정 후 최신 정보를 반환
        return getProfile(userId);
    }

    // 업데이트 로직 분리
    private void updateUserFields(User user, ProfileDTO profileDTO) {
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
    }
}
