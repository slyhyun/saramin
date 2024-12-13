package com.wsd.saramin.apply.service;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.apply.repository.ApplyRepository;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.repository.JobRepository;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplyService(ApplyRepository applyRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.applyRepository = applyRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    // 지원하기
    @Transactional
    public void apply(Long userId, Long jobId) {
        User user = findUserById(userId);
        Job job = findJobById(jobId);

        if (applyRepository.existsByUserAndJob(user, job)) {
            throw new IllegalArgumentException("이미 지원한 공고입니다.");
        }

        Apply apply = new Apply();
        apply.setUser(user);
        apply.setJob(job);
        apply.setStatus(Apply.Status.PENDING);

        applyRepository.save(apply);
    }

    // 사용자별 지원 내역 조회
    @Transactional(readOnly = true)
    public List<ApplyDTO> getApplies(Long userId) {
        User user = findUserById(userId);

        return applyRepository.findAllByUser(user).stream()
                .map(ApplyDTO::new)
                .collect(Collectors.toList());
    }

    // 지원 취소
    @Transactional
    public void cancelApply(Long applyId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("지원 내역을 찾을 수 없습니다."));

        if (apply.getStatus() != Apply.Status.PENDING) {
            throw new IllegalArgumentException("취소할 수 없는 상태입니다.");
        }

        apply.setStatus(Apply.Status.CANCELLED);
        applyRepository.save(apply);
    }

    // 유틸리티 메서드: 사용자 검색
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID " + userId + "에 해당하는 사용자를 찾을 수 없습니다."));
    }

    // 유틸리티 메서드: 채용 공고 검색
    private Job findJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("ID " + jobId + "에 해당하는 채용 공고를 찾을 수 없습니다."));
    }
}
