package com.wsd.saramin.company.dto;

import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.job.dto.JobSummaryDTO;
import com.wsd.saramin.company.entity.Company;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyDTO {
    private long companyId;
    private String name;
    private String type;
    private int employee;
    private String industry;
    private String ceo;
    private String website;
    private String description;
    private String address;
    private String establishment;
    private long revenue;
    private long salary;
    private List<JobSummaryDTO> jobs; // 간소화된 Job 정보
    private List<CompanyBookmarkDTO> companyBookmarks; // Company 북마크 목록


    // Entity => DTO 변환 생성자
    public CompanyDTO(Company company, List<JobSummaryDTO> jobSummaryDTOs, List<CompanyBookmarkDTO> companyBookmarks) {
        this.companyId = company.getCompanyId();
        this.name = company.getName();
        this.type = company.getType();
        this.employee = company.getEmployee();
        this.industry = company.getIndustry();
        this.ceo = company.getCeo();
        this.website = company.getWebsite();
        this.description = company.getDescription();
        this.address = company.getAddress();
        this.establishment = company.getEstablishment();
        this.revenue = company.getRevenue();
        this.salary = company.getSalary();
        this.jobs = jobSummaryDTOs; // JobSummaryDTO 리스트를 할당
        this.companyBookmarks = companyBookmarks;
    }
}
