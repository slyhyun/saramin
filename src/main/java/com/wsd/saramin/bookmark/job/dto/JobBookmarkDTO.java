package com.wsd.saramin.bookmark.job.dto;

import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobBookmarkDTO {
    private Long id;
    private Long userId;
    private Long jobId;
    private LocalDateTime date;

    public JobBookmarkDTO(JobBookmark jobBookmark) {
        this.id = jobBookmark.getId();
        this.userId = jobBookmark.getUser().getUserId();
        this.jobId = jobBookmark.getJob().getJobId();
        this.date = jobBookmark.getDate();

    }
}
