package com.wsd.saramin.company.dto;

import com.wsd.saramin.company.entity.CompanyReview;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompanyReviewDTO {

    private Long companyReviewId;
    private Long companyId;
    private Long userId;
    private String content;
    private LocalDateTime date;

    // Entity -> DTO 변환 생성자
    public CompanyReviewDTO(CompanyReview companyReview) {
        this.companyReviewId = companyReview.getCompanyReviewId();
        this.companyId = companyReview.getCompany().getCompanyId();
        this.userId = companyReview.getUser().getUserId();
        this.content = companyReview.getContent();
        this.date = companyReview.getDate();
    }
}
