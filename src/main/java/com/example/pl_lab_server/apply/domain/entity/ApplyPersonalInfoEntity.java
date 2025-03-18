package com.example.pl_lab_server.apply.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "지원자_개인정보")
@Getter
@Setter
public class ApplyPersonalInfoEntity {

    @Id
    @Column(length = 255)
    private String applicantStdNo;

    @Column(length = 255)
    private String applicantUserEmail;

    @Column(length = 255)
    private String applicantUserName;

    @Column(length = 255)
    private String applicantGrade;

    @Column(length = 255)
    private String applicantUserPhone;
}
