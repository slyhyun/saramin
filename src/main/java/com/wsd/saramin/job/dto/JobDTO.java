package com.wsd.saramin.job.dto;

import com.wsd.saramin.apply.dto.ApplyDTO;
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

    private String companyName; // Company 이름만 포함
    private String userName;    // User 이름만 포함
    private List<ApplyDTO> applies; // Job에 지원한 내역 추가

    // Entity → DTO 변환 생성자
    public JobDTO(Job job, List<ApplyDTO> applyDTOs) {
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

        this.companyName = job.getCompany().getName();
        this.userName = job.getUser().getName();
        this.applies = applyDTOs; // 지원 내역 리스트 추가
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

        // 연관 엔티티 설정
        job.setCompany(company);
        job.setUser(user);

        return job;
    }
}
