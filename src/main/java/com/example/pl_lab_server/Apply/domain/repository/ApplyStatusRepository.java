package com.example.pl_lab_server.Apply.domain.repository;

import com.example.pl_lab_server.Apply.domain.entity.ApplyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyStatusRepository extends JpaRepository<ApplyStatusEntity, String> {
    ApplyStatusEntity findByStatusCode(String statusCode);
    void deleteByStatusCode(String statusCode);
    boolean existsByStatusCode(String statusCode);
}
