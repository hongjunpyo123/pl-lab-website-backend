package com.example.pl_lab_server.User.domain.repository;

import com.example.pl_lab_server.User.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserEmail(String email);
    void deleteByUserEmail(String email);
    boolean existsByUserEmail(String email);
    List<UserEntity> findAllByType(String type);
}
