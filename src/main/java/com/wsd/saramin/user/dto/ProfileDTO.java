package com.wsd.saramin.user.dto;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.bookmark.company.dto.CompanyBookmarkDTO;
import com.wsd.saramin.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileDTO {

    @NotNull(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "전화번호는 10자리에서 15자리 숫자여야 합니다.")
    private String phone;

    @NotNull(message = "지역은 필수입니다.")
    private String region;

    @NotNull(message = "나이는 필수입니다.")
    private int age;

    @NotNull(message = "성별은 필수입니다.")
    private User.Gender gender;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password; // 비밀번호 변경 시 사용

    private List<ApplyDTO> appliedJobs; // 지원 내역
    private List<JobBookmarkDTO> jobBookmarks; // Job 북마크
    private List<CompanyBookmarkDTO> companyBookmarks; // Company 북마크
}
