package com.example.pl_lab_server.apply.domain.repository;

import com.example.pl_lab_server.apply.domain.entity.ApplyPersonalInfoEntity;
import com.example.pl_lab_server.apply.domain.entity.ApplyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyPersonalInfoRepository extends JpaRepository<ApplyPersonalInfoEntity, String> {
    ApplyStatusEntity findByApplicantStdNo(String applicantStdNo);
    void deleteByApplicantStdNo(String applicantStdNo);
    boolean existsByApplicantStdNo(String applicantStdNo);
}
