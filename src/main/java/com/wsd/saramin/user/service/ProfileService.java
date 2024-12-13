package com.wsd.saramin.user.service;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.repository.ApplyRepository;
import com.wsd.saramin.bookmark.company.entity.CompanyBookmark;
import com.wsd.saramin.bookmark.company.repository.CompanyBookmarkRepository;
import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import com.wsd.saramin.bookmark.job.repository.JobBookmarkRepository;
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
    private final ApplyRepository applyRepository;
    private final JobBookmarkRepository jobBookmarkRepository;
    private final CompanyBookmarkRepository companyBookmarkRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserRepository userRepository,
                          ApplyRepository applyRepository,
                          JobBookmarkRepository jobBookmarkRepository,
                          CompanyBookmarkRepository companyBookmarkRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.applyRepository = applyRepository;
        this.jobBookmarkRepository = jobBookmarkRepository;
        this.companyBookmarkRepository = companyBookmarkRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 프로필 조회
    @Transactional(readOnly = true)
    public ProfileDTO getProfile(Long userId) {
        User user = findUserById(userId);

        return mapToProfileDTO(user);
    }

    // 사용자 프로필 수정
    @Transactional
    public ProfileDTO updateProfile(Long userId, ProfileDTO profileDTO) {
        User user = findUserById(userId);

        updateUserFields(user, profileDTO);
        userRepository.save(user);

        // 수정 후 최신 정보를 반환
        return mapToProfileDTO(user);
    }

    // Job 북마크 삭제
    @Transactional
    public void deleteJobBookmark(Long userId, Long jobBookmarkId) {
        User user = findUserById(userId);

        JobBookmark jobBookmark = jobBookmarkRepository.findById(jobBookmarkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 JobBookmark를 찾을 수 없습니다."));

        if (!jobBookmark.getUser().equals(user)) {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }

        jobBookmarkRepository.delete(jobBookmark);
    }

    // Company 북마크 삭제
    @Transactional
    public void deleteCompanyBookmark(Long userId, Long companyBookmarkId) {
        User user = findUserById(userId);

        CompanyBookmark companyBookmark = companyBookmarkRepository.findById(companyBookmarkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 CompanyBookmark를 찾을 수 없습니다."));

        if (!companyBookmark.getUser().equals(user)) {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }

        companyBookmarkRepository.delete(companyBookmark);
    }

    // User 엔티티에서 ProfileDTO로 매핑
    private ProfileDTO mapToProfileDTO(User user) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(user.getEmail());
        profileDTO.setName(user.getName());
        profileDTO.setPhone(user.getPhone());
        profileDTO.setRegion(user.getRegion());
        profileDTO.setAge(user.getAge());
        profileDTO.setGender(user.getGender());
        profileDTO.setAppliedJobs(
                applyRepository.findAllByUser(user).stream()
                        .map(ApplyDTO::new)
                        .collect(Collectors.toList())
        );
        return profileDTO;
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

    // 사용자 검색 로직 분리
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID " + userId + "에 해당하는 사용자를 찾을 수 없습니다."));
    }
}
