package com.wsd.saramin.company.service;

import com.wsd.saramin.company.dto.CompanyReviewDTO;
import com.wsd.saramin.company.entity.Company;
import com.wsd.saramin.company.entity.CompanyReview;
import com.wsd.saramin.company.repository.CompanyRepository;
import com.wsd.saramin.company.repository.CompanyReviewRepository;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyReviewService {

    private final CompanyReviewRepository companyReviewRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyReviewService(CompanyReviewRepository companyReviewRepository,
                                CompanyRepository companyRepository,
                                UserRepository userRepository) {
        this.companyReviewRepository = companyReviewRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addCompanyReview(Long userId, Long companyId, String content) {
        User user = findUserById(userId);
        Company company = findCompanyById(companyId);

        CompanyReview review = new CompanyReview();
        review.setUser(user);
        review.setCompany(company);
        review.setContent(content);

        companyReviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<CompanyReviewDTO> getCompanyReviews(Long companyId) {
        Company company = findCompanyById(companyId);

        return companyReviewRepository.findByCompany(company).stream()
                .map(CompanyReviewDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCompanyReview(Long userId, Long reviewId) {
        CompanyReview review = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser().getUserId() != userId) {
            throw new IllegalArgumentException("리뷰를 삭제할 권한이 없습니다.");
        }

        companyReviewRepository.delete(review);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Company findCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("회사를 찾을 수 없습니다."));
    }
}
