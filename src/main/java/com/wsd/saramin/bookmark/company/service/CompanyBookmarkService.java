package com.wsd.saramin.bookmark.company.service;

import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.company.entity.CompanyBookmark;
import com.wsd.saramin.bookmark.company.repository.CompanyBookmarkRepository;
import com.wsd.saramin.company.entity.Company;
import com.wsd.saramin.company.repository.CompanyRepository;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyBookmarkService {

    private final CompanyBookmarkRepository companyBookmarkRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public CompanyBookmarkService(CompanyBookmarkRepository companyBookmarkRepository,
                                  UserRepository userRepository,
                                  CompanyRepository companyRepository) {
        this.companyBookmarkRepository = companyBookmarkRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void toggleBookmark(Long userId, Long companyId) {
        User user = findUserById(userId);
        Company company = findCompanyById(companyId);

        if (companyBookmarkRepository.existsByUserAndCompany(user, company)) {
            companyBookmarkRepository.deleteByUserAndCompany(user, company);
        } else {
            CompanyBookmark bookmark = new CompanyBookmark();
            bookmark.setUser(user);
            bookmark.setCompany(company);
            companyBookmarkRepository.save(bookmark);
        }
    }

    @Transactional(readOnly = true)
    public List<CompanyBookmarkDTO> getBookmarks(Long userId, int page) {
        User user = findUserById(userId);

        // 페이지네이션 및 정렬 설정
        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));

        return companyBookmarkRepository.findByUser(user, pageable).stream()
                .map(CompanyBookmarkDTO::new)
                .collect(Collectors.toList());
    }


    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID " + userId + "에 해당하는 사용자를 찾을 수 없습니다."));
    }

    private Company findCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("ID " + companyId + "에 해당하는 회사를 찾을 수 없습니다."));
    }
}
