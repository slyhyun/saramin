package com.wsd.saramin.job.service;

import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // 채용 공고 목록 조회 (페이지네이션, 필터링, 정렬)
    public Page<JobDTO> getJobs(String location, String experience, String sector, String keyword, Pageable pageable) {
        // 필터링 및 검색 로직 추가 (Specification 사용 가능)
        return jobRepository.findAll(pageable)
                .map(JobDTO::new); // Job -> JobDTO 변환
    }

    // 채용 공고 상세 조회
    @Transactional(readOnly = true)
    public JobDTO getJobById(long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("채용 공고를 찾을 수 없습니다."));
        return new JobDTO(job);
    }

    // 채용 공고 등록
    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = jobDTO.toEntity(null, null); // Company와 User는 추가 로직으로 설정
        Job savedJob = jobRepository.save(job);
        return new JobDTO(savedJob);
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
        return new JobDTO(updatedJob);
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
