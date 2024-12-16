package com.wsd.saramin.bookmark.job.service;

import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import com.wsd.saramin.bookmark.job.repository.JobBookmarkRepository;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.repository.JobRepository;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobBookmarkService {

    private final JobBookmarkRepository jobBookmarkRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public JobBookmarkService(JobBookmarkRepository jobBookmarkRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.jobBookmarkRepository = jobBookmarkRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @Transactional
    public void toggleJobBookmark(Long userId, Long jobId) {
        User user = findUserById(userId);
        Job job = findJobById(jobId);

        if (jobBookmarkRepository.existsByUserAndJob(user, job)) {
            jobBookmarkRepository.deleteByUserAndJob(user, job);
        } else {
            JobBookmark jobBookmark = new JobBookmark();
            jobBookmark.setUser(user);
            jobBookmark.setJob(job);
            jobBookmarkRepository.save(jobBookmark);
        }
    }

    @Transactional(readOnly = true)
    public List<JobBookmarkDTO> getJobBookmarks(Long userId, int page) {
        User user = findUserById(userId);

        // 페이지네이션 및 정렬 설정
        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));

        return jobBookmarkRepository.findByUser(user, pageable).stream()
                .map(JobBookmarkDTO::new)
                .collect(Collectors.toList());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Job findJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용 공고를 찾을 수 없습니다."));
    }
}
