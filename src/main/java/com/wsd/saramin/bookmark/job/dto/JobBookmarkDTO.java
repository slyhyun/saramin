package com.wsd.saramin.bookmark.job.dto;

import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobBookmarkDTO {
    private Long id;
    private Long userId;
    private Long jobId;

    public JobBookmarkDTO(JobBookmark jobBookmark) {
        this.id = jobBookmark.getId();
        this.userId = jobBookmark.getUser().getUserId();
        this.jobId = jobBookmark.getJob().getJobId();
    }
}
