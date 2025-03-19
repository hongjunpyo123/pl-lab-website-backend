package com.example.pl_lab_server.Apply.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "심사상태")
public class ApplyStatusEntity {

    @Id
    @Column(length = 255)
    private String statusCode;

    @Column(length = 255)
    private String statusDescription;
}