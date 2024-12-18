package com.wsd.saramin.bookmark.company.dto;

import com.wsd.saramin.bookmark.company.entity.CompanyBookmark;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompanyBookmarkDTO {
    private Long id;
    private Long userId;
    private Long companyId;
    private String companyName;
    private LocalDateTime date;

    public CompanyBookmarkDTO(CompanyBookmark companyBookmark) {
        this.id = companyBookmark.getId();
        this.userId = companyBookmark.getUser().getUserId();
        this.companyId = companyBookmark.getCompany().getCompanyId();
        this.companyName = companyBookmark.getCompany().getName();
        this.date = companyBookmark.getDate();
    }
}
