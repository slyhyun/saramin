package com.wsd.saramin.job.service;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.repository.ApplyRepository;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.bookmark.job.repository.JobBookmarkRepository;
import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.job.dto.JobReviewDTO;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.repository.JobRepository;
import com.wsd.saramin.job.repository.JobReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ApplyRepository applyRepository;
    private final JobBookmarkRepository jobBookmarkRepository;
    private final JobReviewRepository jobReviewRepository; // 추가

    public JobService(JobRepository jobRepository, ApplyRepository applyRepository,
                      JobBookmarkRepository jobBookmarkRepository, JobReviewRepository jobReviewRepository) {
        this.jobRepository = jobRepository;
        this.applyRepository = applyRepository;
        this.jobBookmarkRepository = jobBookmarkRepository;
        this.jobReviewRepository = jobReviewRepository; // 추가
    }

    // 채용 공고 목록 조회 (페이지네이션, 필터링, 정렬)
    public Page<JobDTO> getJobs(String location, String experience, String sector, String keyword, Pageable pageable) {
        return jobRepository.findAll(pageable)
                .map(job -> {
                    var applyDTOs = applyRepository.findAllByJob(job).stream()
                            .map(ApplyDTO::new)
                            .collect(Collectors.toList());
                    var jobBookmarkDTOs = jobBookmarkRepository.findByJob(job).stream()
                            .map(JobBookmarkDTO::new)
                            .collect(Collectors.toList());
                    var jobReviewDTOs = jobReviewRepository.findByJob(job).stream() // 추가
                            .map(JobReviewDTO::new)
                            .collect(Collectors.toList());
                    return new JobDTO(job, applyDTOs, jobBookmarkDTOs, jobReviewDTOs); // 수정
                });
    }

    // 채용 공고 상세 조회
    @Transactional(readOnly = true)
    public JobDTO getJobById(long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("채용 공고를 찾을 수 없습니다."));
        var applyDTOs = applyRepository.findAllByJob(job).stream()
                .map(ApplyDTO::new)
                .collect(Collectors.toList());
        var jobBookmarkDTOs = jobBookmarkRepository.findByJob(job).stream()
                .map(JobBookmarkDTO::new)
                .collect(Collectors.toList());
        var jobReviewDTOs = jobReviewRepository.findByJob(job).stream() // 추가
                .map(JobReviewDTO::new)
                .collect(Collectors.toList());
        return new JobDTO(job, applyDTOs, jobBookmarkDTOs, jobReviewDTOs); // 수정
    }

    // 채용 공고 등록
    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = jobDTO.toEntity(null, null);
        Job savedJob = jobRepository.save(job);
        return new JobDTO(savedJob, List.of(), List.of(), List.of()); // 빈 리스트로 초기화
    }

    // 채용 공고 수정
    @Transactional
    public JobDTO updateJob(long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("채용 공고를 찾을 수 없습니다."));
        job.setTitle(jobDTO.getTitle());
        job.setLocation(jobDTO.getLocation());
        job.setExperience(jobDTO.getExperience());
        job.setType(jobDTO.getType());
        job.setEducation(jobDTO.getEducation());
        job.setDeadline(jobDTO.getDeadline());
        job.setSector(jobDTO.getSector());
        Job updatedJob = jobRepository.save(job);

        var applyDTOs = applyRepository.findAllByJob(updatedJob).stream()
                .map(ApplyDTO::new)
                .collect(Collectors.toList());
        var jobBookmarkDTOs = jobBookmarkRepository.findByJob(updatedJob).stream()
                .map(JobBookmarkDTO::new)
                .collect(Collectors.toList());
        var jobReviewDTOs = jobReviewRepository.findByJob(updatedJob).stream() // 추가
                .map(JobReviewDTO::new)
                .collect(Collectors.toList());
        return new JobDTO(updatedJob, applyDTOs, jobBookmarkDTOs, jobReviewDTOs); // 수정
    }

    // 채용 공고 삭제
    @Transactional
    public void deleteJob(long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("채용 공고를 찾을 수 없습니다.");
        }
        jobRepository.deleteById(id);
    }
}
