package com.example.pl_lab_server.Apply.domain.repository;

import com.example.pl_lab_server.Apply.domain.entity.ApplyHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyHistoryRepository extends JpaRepository<ApplyHistoryEntity, String> {
    ApplyHistoryEntity findByApplicantStdNo(String applicantStdNo);
    void deleteByApplicantStdNo(String applicantStdNo);
    boolean existsByApplicantStdNo(String applicantStdNo);
}
