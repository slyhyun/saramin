package com.wsd.saramin.job.dto;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.company.entity.Company;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class JobDTO {
    private long jobId;
    private String title;
    private String location;
    private String experience;
    private String type;
    private String education;
    private String deadline;
    private String sector;
    private long view;
    private LocalDateTime date;

    private String companyName; // 회사 이름
    private String userName;    // 사용자 이름
    private String link;        // 공고 링크
    private List<ApplyDTO> applies; // Job에 지원한 내역 추가
    private List<JobBookmarkDTO> jobBookmarks; // Job 북마크 목록
    private List<JobReviewDTO> jobReviews; // Job 리뷰 목록 추가

    // 새로운 필드: 관련 공고 목록
    private List<JobDTO> relatedJobs;

    // Entity → DTO 변환 생성자
    public JobDTO(Job job, List<ApplyDTO> applyDTOs, List<JobBookmarkDTO> jobBookmarkDTOs, List<JobReviewDTO> jobReviewDTOs) {
        this.jobId = job.getJobId();
        this.title = job.getTitle();
        this.location = job.getLocation();
        this.experience = job.getExperience();
        this.type = job.getType();
        this.education = job.getEducation();
        this.deadline = job.getDeadline();
        this.sector = job.getSector();
        this.view = job.getView();
        this.date = job.getDate();

        this.companyName = job.getCompanyName(); // 엔티티에서 직접 저장된 회사 이름 사용
        this.userName = job.getUser() != null ? job.getUser().getName() : null;
        this.link = job.getLink(); // 링크 추가

        this.applies = applyDTOs;
        this.jobBookmarks = jobBookmarkDTOs;
        this.jobReviews = jobReviewDTOs;
    }

    // DTO → Entity 변환 메서드
    public Job toEntity(Company company, User user) {
        Job job = new Job();
        job.setTitle(this.title);
        job.setLocation(this.location);
        job.setExperience(this.experience);
        job.setType(this.type);
        job.setEducation(this.education);
        job.setDeadline(this.deadline);
        job.setSector(this.sector);
        job.setLink(this.link);

        // 연관 엔티티 설정
        job.setCompany(company);
        job.setUser(user);

        return job;
    }
}
