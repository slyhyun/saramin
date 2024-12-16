package com.wsd.saramin.apply.service;

import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.entity.Apply;
import com.wsd.saramin.apply.entity.Apply.Status;
import com.wsd.saramin.apply.repository.ApplyRepository;
import com.wsd.saramin.job.repository.JobRepository;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        apply.setStatus(Status.PENDING);

        applyRepository.save(apply);
    }

    // 사용자별 지원 내역 조회 (페이지네이션 및 상태별 필터링)
    @Transactional(readOnly = true)
    public List<ApplyDTO> getApplies(Long userId, String status, String page) {
        User user = findUserById(userId);

        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page) - 1; // 1-based index를 0-based index로 변환
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("페이지 번호는 숫자여야 합니다.");
        }

        // 페이지네이션 설정 (기본 20개, 최신순 정렬)
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "date"));

        if (status != null) {
            Status applyStatus;
            try {
                applyStatus = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 상태 값입니다: " + status);
            }

            return applyRepository.findAllByUserAndStatus(user, applyStatus, pageable)
                    .stream()
                    .map(ApplyDTO::new)
                    .collect(Collectors.toList());
        }

        return applyRepository.findAllByUser(user, pageable)
                .stream()
                .map(ApplyDTO::new)
                .collect(Collectors.toList());
    }

    // 지원 취소
    @Transactional
    public void cancelApply(Long applyId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("지원 내역을 찾을 수 없습니다."));

        if (apply.getStatus() != Status.PENDING) {
            throw new IllegalArgumentException("취소할 수 없는 상태입니다.");
        }

        apply.setStatus(Status.CANCELLED);
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
