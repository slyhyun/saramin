package com.wsd.saramin.job.controller;

import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.job.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // 채용 공고 목록 조회 (페이지네이션, 필터링, 정렬)
    @GetMapping
    public ResponseEntity<Page<JobDTO>> getJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experience,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        Page<JobDTO> jobs = jobService.getJobs(location, experience, sector, keyword, pageable);
        return ResponseEntity.ok(jobs);
    }

    // 채용 공고 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable long id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    // 채용 공고 등록
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO createdJob = jobService.createJob(jobDTO);
        return ResponseEntity.ok(createdJob);
    }

    // 채용 공고 수정
    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable long id, @RequestBody JobDTO jobDTO) {
        JobDTO updatedJob = jobService.updateJob(id, jobDTO);
        return ResponseEntity.ok(updatedJob);
    }

    // 채용 공고 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
