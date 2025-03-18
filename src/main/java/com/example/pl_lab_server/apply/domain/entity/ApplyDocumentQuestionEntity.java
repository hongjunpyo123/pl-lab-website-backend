package com.example.pl_lab_server.apply.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "서류_질문")
@Getter
@Setter
public class ApplyDocumentQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 설정
    private Long questionId;

    @Column(length = 255)
    private String questionText;

    @Column(length = 1024)
    private String questionDescription;

    @Column(length = 255)
    private String questionRegDt;
}
