package com.example.pl_lab_server.Apply.domain.repository;

import com.example.pl_lab_server.Apply.domain.entity.ApplyResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyResponseRepository extends JpaRepository<ApplyResponseEntity, Long> {
    List<ApplyResponseEntity> findByApplicantStdNo(String applicantStdNo);
    void deleteByApplicantStdNo(String applicantStdNo);
    boolean existsByApplicantStdNo(String applicantStdNo);
}
