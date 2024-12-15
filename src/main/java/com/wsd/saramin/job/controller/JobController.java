package com.wsd.saramin.job.controller;

import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job Management", description = "채용 공고 관리 API")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(
            summary = "채용 공고 목록 조회",
            description = "채용 공고를 페이지네이션, 필터링, 정렬 옵션으로 조회합니다.",
            parameters = {
                    @Parameter(name = "location", description = "채용 공고의 지역 필터", required = false),
                    @Parameter(name = "experience", description = "경력 필터", required = false),
                    @Parameter(name = "sector", description = "산업 섹터 필터", required = false),
                    @Parameter(name = "keyword", description = "검색 키워드", required = false),
            }
    )
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

    @Operation(
            summary = "채용 공고 상세 조회",
            description = "특정 ID를 가진 채용 공고의 상세 정보를 조회합니다.",
            parameters = @Parameter(name = "id", description = "조회할 채용 공고의 ID", required = true)
    )
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable long id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @Operation(
            summary = "채용 공고 등록",
            description = "새로운 채용 공고를 등록합니다."
    )
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO createdJob = jobService.createJob(jobDTO);
        return ResponseEntity.ok(createdJob);
    }

    @Operation(
            summary = "채용 공고 수정",
            description = "기존의 채용 공고를 수정합니다.",
            parameters = @Parameter(name = "id", description = "수정할 채용 공고의 ID", required = true)
    )
    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable long id, @RequestBody JobDTO jobDTO) {
        JobDTO updatedJob = jobService.updateJob(id, jobDTO);
        return ResponseEntity.ok(updatedJob);
    }

    @Operation(
            summary = "채용 공고 삭제",
            description = "특정 ID를 가진 채용 공고를 삭제합니다.",
            parameters = @Parameter(name = "id", description = "삭제할 채용 공고의 ID", required = true)
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
