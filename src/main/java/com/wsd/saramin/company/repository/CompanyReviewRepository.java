package com.wsd.saramin.company.repository;

import com.wsd.saramin.company.entity.CompanyReview;
import com.wsd.saramin.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
    List<CompanyReview> findByCompany(Company company);
}
