package com.wsd.saramin.job.dto;

import com.wsd.saramin.job.entity.Job;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSummaryDTO {
    private long jobId;   // Job ID
    private String title; // Job 제목

    // Entity → DTO 변환 생성자
    public JobSummaryDTO(Job job) {
        this.jobId = job.getJobId();
        this.title = job.getTitle();
    }
}
