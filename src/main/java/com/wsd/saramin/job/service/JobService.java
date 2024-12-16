package com.wsd.saramin.job.service;

import com.wsd.saramin.apply.dto.ApplyDTO;
import com.wsd.saramin.apply.repository.ApplyRepository;
import com.wsd.saramin.bookmark.job.dto.JobBookmarkDTO;
import com.wsd.saramin.bookmark.job.repository.JobBookmarkRepository;
import com.wsd.saramin.job.dto.JobDTO;
import com.wsd.saramin.job.entity.Job;
import com.wsd.saramin.job.repository.JobRepository;
import com.wsd.saramin.job.repository.JobReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ApplyRepository applyRepository;
    private final JobBookmarkRepository jobBookmarkRepository;
    private final JobReviewRepository jobReviewRepository;

    public JobService(JobRepository jobRepository, ApplyRepository applyRepository,
                      JobBookmarkRepository jobBookmarkRepository, JobReviewRepository jobReviewRepository) {
        this.jobRepository = jobRepository;
        this.applyRepository = applyRepository;
        this.jobBookmarkRepository = jobBookmarkRepository;
        this.jobReviewRepository = jobReviewRepository;
    }

    public Page<JobDTO> getJobs(String region, String experience, String sector, String title, String companyName,
                                String keyword, Pageable pageable) {
        return jobRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (region != null) {
                predicates.add(criteriaBuilder.equal(root.get("region"), region));
            }
            if (experience != null) {
                predicates.add(criteriaBuilder.equal(root.get("experience"), experience));
            }
            if (sector != null) {
                predicates.add(criteriaBuilder.equal(root.get("sector"), sector));
            }

            if (title != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (companyName != null) {
                predicates.add(criteriaBuilder.like(root.get("companyName"), "%" + companyName + "%"));
            }
            if (keyword != null) {
                predicates.add(criteriaBuilder.like(root.get("sector"), "%" + keyword + "%"));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(job -> {
            var applyDTOs = applyRepository.findAllByJob(job).stream()
                    .map(ApplyDTO::new)
                    .collect(Collectors.toList());
            var jobBookmarkDTOs = jobBookmarkRepository.findByJob(job).stream()
                    .map(JobBookmarkDTO::new)
                    .collect(Collectors.toList());
            return new JobDTO(job, applyDTOs, jobBookmarkDTOs, List.of());
        });
    }

    public Sort getSort(String sortBy) {
        switch (sortBy) {
            case "view":
                return Sort.by(Sort.Direction.DESC, "viewCount");
            case "apply":
                return Sort.by(Sort.Direction.DESC, "applyCount");
            case "date":
            default:
                return Sort.by(Sort.Direction.DESC, "createdDate");
        }
    }

    @Transactional
    public JobDTO getJobByIdAndIncrementView(long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("채용 공고를 찾을 수 없습니다."));

        // 조회수 증가
        job.incrementView();
        jobRepository.save(job);

        var applyDTOs = applyRepository.findAllByJob(job).stream()
                .map(ApplyDTO::new)
                .collect(Collectors.toList());
        var jobBookmarkDTOs = jobBookmarkRepository.findByJob(job).stream()
                .map(JobBookmarkDTO::new)
                .collect(Collectors.toList());

        return new JobDTO(job, applyDTOs, jobBookmarkDTOs, List.of());
    }

    @Transactional(readOnly = true)
    public List<JobDTO> getRelatedJobs(String sector) {
        List<Job> relatedJobs = jobRepository.findBySector(sector);

        return relatedJobs.stream()
                .map(job -> {
                    var applyDTOs = applyRepository.findAllByJob(job).stream()
                            .map(ApplyDTO::new)
                            .collect(Collectors.toList());
                    var jobBookmarkDTOs = jobBookmarkRepository.findByJob(job).stream()
                            .map(JobBookmarkDTO::new)
                            .collect(Collectors.toList());
                    return new JobDTO(job, applyDTOs, jobBookmarkDTOs, List.of());
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = jobDTO.toEntity(null, null);
        job.setCompanyName(job.getCompany().getName());
        Job savedJob = jobRepository.save(job);
        return new JobDTO(savedJob, List.of(), List.of(), List.of());
    }

    @Transactional
    public JobDTO updateJob(long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("채용 공고를 찾을 수 없습니다."));
        job.setTitle(jobDTO.getTitle());
        job.setLocation(jobDTO.getLocation());
        job.setExperience(jobDTO.getExperience());
        job.setType(jobDTO.getType());
        job.setEducation(jobDTO.getEducation());
        job.setDeadline(jobDTO.getDeadline());
        job.setSector(jobDTO.getSector());
        job.setLink(jobDTO.getLink());
        job.setCompanyName(job.getCompany().getName());

        Job updatedJob = jobRepository.save(job);

        var applyDTOs = applyRepository.findAllByJob(updatedJob).stream()
                .map(ApplyDTO::new)
                .collect(Collectors.toList());
        var jobBookmarkDTOs = jobBookmarkRepository.findByJob(updatedJob).stream()
                .map(JobBookmarkDTO::new)
                .collect(Collectors.toList());
        return new JobDTO(updatedJob, applyDTOs, jobBookmarkDTOs, List.of());
    }

    @Transactional
    public void deleteJob(long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("채용 공고를 찾을 수 없습니다.");
        }
        jobRepository.deleteById(id);
    }
}
