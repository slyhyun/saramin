package com.wsd.saramin.user.repository;

import com.wsd.saramin.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 검색
    Optional<User> findByEmail(String email);

    // 이메일 중복 여부 확인
    boolean existsByEmail(String email);
}
