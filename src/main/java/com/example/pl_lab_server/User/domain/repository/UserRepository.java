package com.example.pl_lab_server.User.domain.repository;

import com.example.pl_lab_server.User.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserEmail(String email);
    void deleteByUserEmail(String email);
    boolean existsByUserEmail(String email);
}
