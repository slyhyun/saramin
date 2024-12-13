package com.wsd.saramin.bookmark.company.repository;

import com.wsd.saramin.bookmark.company.entity.CompanyBookmark;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyBookmarkRepository extends JpaRepository<CompanyBookmark, Long> {
    boolean existsByUserAndCompany(User user, Company company);

    List<CompanyBookmark> findByUser(User user);
    List<CompanyBookmark> findByCompany(Company company);

    @Modifying
    @Transactional
    @Query("DELETE FROM CompanyBookmark cb WHERE cb.user = :user AND cb.company = :company")
    void deleteByUserAndCompany(User user, Company company);
}
