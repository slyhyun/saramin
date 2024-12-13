package com.wsd.saramin.bookmark.job.repository;

import com.wsd.saramin.bookmark.job.entity.JobBookmark;
import com.wsd.saramin.user.entity.User;
import com.wsd.saramin.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobBookmarkRepository extends JpaRepository<JobBookmark, Long> {
    boolean existsByUserAndJob(User user, Job job);
    List<JobBookmark> findByUser(User user);
    List<JobBookmark> findByJob(Job job);

    @Modifying
    @Transactional
    @Query("DELETE FROM JobBookmark jb WHERE jb.user = :user AND jb.job = :job")
    void deleteByUserAndJob(User user, Job job);
}
