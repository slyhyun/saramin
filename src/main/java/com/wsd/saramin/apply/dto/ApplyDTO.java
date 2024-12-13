package com.wsd.saramin.apply.dto;

import com.wsd.saramin.apply.entity.Apply;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplyDTO {

    private Long applyId; // 기존 id를 더 명확히 변경
    private Long userId;
    private Long jobId;
    private String jobTitle;
    private LocalDateTime applyDate; // 기존 appliedAt에서 변경
    private String status;

    // Entity → DTO 변환 생성자
    public ApplyDTO(Apply apply) {
        this.applyId = apply.getApplyId(); // 변경된 엔티티 메서드
        this.userId = apply.getUser().getUserId();
        this.jobId = apply.getJob().getJobId();
        this.jobTitle = apply.getJob().getTitle();
        this.applyDate = apply.getDate(); // appliedAt → date로 변경
        this.status = apply.getStatus().name();
    }
}
