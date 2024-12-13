package com.wsd.saramin.company.service;

import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.company.repository.CompanyBookmarkRepository;
import com.wsd.saramin.company.dto.CompanyDTO;
import com.wsd.saramin.company.entity.Company;
import com.wsd.saramin.company.repository.CompanyRepository;
import com.wsd.saramin.job.dto.JobSummaryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyBookmarkRepository companyBookmarkRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyBookmarkRepository companyBookmarkRepository) {
        this.companyRepository = companyRepository;
        this.companyBookmarkRepository = companyBookmarkRepository;
    }

    // 회사 정보 조회
    @Transactional(readOnly = true)
    public CompanyDTO getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("회사를 찾을 수 없습니다."));

        var jobSummaries = company.getJobs().stream()
                .map(JobSummaryDTO::new) // Job -> JobSummaryDTO 변환
                .collect(Collectors.toList());

        var companyBookmarks = companyBookmarkRepository.findByCompany(company).stream()
                .map(CompanyBookmarkDTO::new) // CompanyBookmark -> CompanyBookmarkDTO 변환
                .collect(Collectors.toList());

        return new CompanyDTO(company, jobSummaries, companyBookmarks);
    }

    // 회사 정보 수정
    @Transactional
    public CompanyDTO updateCompany(Long companyId, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("회사를 찾을 수 없습니다."));

        updateCompanyFields(company, companyDTO);
        companyRepository.save(company);

        // 수정된 정보 반환
        return getCompany(companyId);
    }

    // 회사 필드 업데이트
    private void updateCompanyFields(Company company, CompanyDTO companyDTO) {
        if (companyDTO.getName() != null) company.setName(companyDTO.getName());
        if (companyDTO.getType() != null) company.setType(companyDTO.getType());
        if (companyDTO.getIndustry() != null) company.setIndustry(companyDTO.getIndustry());
        if (companyDTO.getCeo() != null) company.setCeo(companyDTO.getCeo());
        if (companyDTO.getWebsite() != null) company.setWebsite(companyDTO.getWebsite());
        if (companyDTO.getDescription() != null) company.setDescription(companyDTO.getDescription());
        if (companyDTO.getAddress() != null) company.setAddress(companyDTO.getAddress());
        if (companyDTO.getEstablishment() != null) company.setEstablishment(companyDTO.getEstablishment());
        if (companyDTO.getEmployee() > 0) company.setEmployee(companyDTO.getEmployee());
        if (companyDTO.getRevenue() > 0) company.setRevenue(companyDTO.getRevenue());
        if (companyDTO.getSalary() > 0) company.setSalary(companyDTO.getSalary());
    }
}
