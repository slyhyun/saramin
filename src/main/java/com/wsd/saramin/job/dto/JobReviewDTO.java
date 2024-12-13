package com.wsd.saramin.job.dto;

import com.wsd.saramin.job.entity.JobReview;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobReviewDTO {

    private Long jobReviewId;
    private Long jobId;
    private Long userId;
    private String userName; // 사용자 이름 추가
    private String content;
    private LocalDateTime date;

    public JobReviewDTO(JobReview jobReview) {
        this.jobReviewId = jobReview.getJobReviewId();
        this.jobId = jobReview.getJob().getJobId();
        this.userId = jobReview.getUser().getUserId();
        this.userName = jobReview.getUser().getName(); // 사용자 이름
        this.content = jobReview.getContent();
        this.date = jobReview.getDate();
    }
}
