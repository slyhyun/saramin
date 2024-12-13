package com.wsd.saramin.application.dto;

import com.wsd.saramin.application.entity.Application;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationDTO {

    private Long applicationId; // 기존 id를 더 명확히 변경
    private Long userId;
    private Long jobId;
    private String jobTitle;
    private LocalDateTime applicationDate; // 기존 appliedAt에서 변경
    private String status;

    // Entity → DTO 변환 생성자
    public ApplicationDTO(Application application) {
        this.applicationId = application.getApplicationId(); // 변경된 엔티티 메서드
        this.userId = application.getUser().getUserId();
        this.jobId = application.getJob().getJobId();
        this.jobTitle = application.getJob().getTitle();
        this.applicationDate = application.getDate(); // appliedAt → date로 변경
        this.status = application.getStatus().name();
    }
}
