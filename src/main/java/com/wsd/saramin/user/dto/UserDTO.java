package com.wsd.saramin.user.dto;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.job.dto.JobReviewDTO;
import com.wsd.saramin.company.dto.CompanyReviewDTO;
import com.wsd.saramin.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private long userId;
    private String email;
    private String name;
    private int age;
    private String phone;
    private String region;
    private User.Gender gender;
    private User.Role role;
    private LocalDateTime register;
    private List<JobDTO> jobs;
    private List<ApplyDTO> applies; // 사용자 지원 내역 DTO
    private List<JobBookmarkDTO> jobBookmarks; // Job 북마크 목록
    private List<CompanyBookmarkDTO> companyBookmarks; // Company 북마크 목록
    private List<JobReviewDTO> jobReviews; // Job 리뷰 목록 추가
    private List<CompanyReviewDTO> companyReviews; // Company 리뷰 목록 추가

    // Entity => DTO 변환 생성자
    public UserDTO(User user,
                   List<JobDTO> jobDTOs,
                   List<ApplyDTO> applyDTOs,
                   List<JobBookmarkDTO> jobBookmarkDTOs,
                   List<CompanyBookmarkDTO> companyBookmarkDTOs,
                   List<JobReviewDTO> jobReviewDTOs,
                   List<CompanyReviewDTO> companyReviewDTOs) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.region = user.getRegion();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.register = user.getRegister();
        this.jobs = jobDTOs;
        this.applies = applyDTOs;
        this.jobBookmarks = jobBookmarkDTOs;
        this.companyBookmarks = companyBookmarkDTOs;
        this.jobReviews = jobReviewDTOs; // Job 리뷰 추가
        this.companyReviews = companyReviewDTOs; // Company 리뷰 추가
    }
}
