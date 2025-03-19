package com.example.pl_lab_server.Apply.domain.repository;

import com.example.pl_lab_server.Apply.domain.entity.ApplyPersonalInfoEntity;
import com.example.pl_lab_server.Apply.domain.entity.ApplyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyPersonalInfoRepository extends JpaRepository<ApplyPersonalInfoEntity, String> {
    ApplyStatusEntity findByApplicantStdNo(String applicantStdNo);
    void deleteByApplicantStdNo(String applicantStdNo);
    boolean existsByApplicantStdNo(String applicantStdNo);
}
