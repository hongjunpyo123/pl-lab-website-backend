package com.example.pl_lab_server.Apply.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "지원자_내역")
public class ApplyHistoryEntity {
    @Id
    @Column(length = 255)
    private String applicantStdNo;

    @Column(length = 255)
    private String applicantUserName;

    @Column(length = 255)
    private String applicantRegDt;

    @Column(length = 255)
    private String statusCode;
}
