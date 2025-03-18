package com.example.pl_lab_server.apply.domain.repository;

import com.example.pl_lab_server.apply.domain.entity.ApplyDocumentQuestionEntity;
import com.example.pl_lab_server.apply.domain.entity.ApplyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyDocumentQuestionRepository extends JpaRepository<ApplyDocumentQuestionEntity, Long> {
    boolean existsByQuestionId(Long questionId);
}
