package com.example.pl_lab_server.Apply.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "서류_응답")
public class ApplyResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @Column(length = 255)
    private String applicantStdNo;

    @Column(length = 255)
    private String questionId;

    @Column(length = 10000)
    private String applicantResponse;

}
