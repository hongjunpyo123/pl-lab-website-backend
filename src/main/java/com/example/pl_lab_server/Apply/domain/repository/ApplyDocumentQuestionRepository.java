package com.example.pl_lab_server.Apply.domain.repository;

import com.example.pl_lab_server.Apply.domain.entity.ApplyDocumentQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyDocumentQuestionRepository extends JpaRepository<ApplyDocumentQuestionEntity, Long> {
    boolean existsByQuestionId(Long questionId);
}
