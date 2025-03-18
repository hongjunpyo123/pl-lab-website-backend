package com.example.pl_lab_server.apply.dto;

import com.example.pl_lab_server.apply.domain.entity.ApplyPersonalInfoEntity;
import com.example.pl_lab_server.apply.domain.entity.ApplyStatusEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyPersonalInfoDto {
    private String applicantStdNo;
    private String applicantUserEmail;
    private String applicantUserName;
    private String applicantGrade;
    private String applicantUserPhone;

    public ApplyPersonalInfoEntity toEntity(){
        ApplyPersonalInfoEntity applyPersonalInfoEntity = new ApplyPersonalInfoEntity();
        applyPersonalInfoEntity.setApplicantStdNo(this.getApplicantStdNo());
        applyPersonalInfoEntity.setApplicantUserEmail(this.getApplicantUserEmail());
        applyPersonalInfoEntity.setApplicantUserName(this.getApplicantUserName());
        applyPersonalInfoEntity.setApplicantGrade(this.getApplicantGrade());
        applyPersonalInfoEntity.setApplicantUserPhone(this.getApplicantUserPhone());
        return applyPersonalInfoEntity;
    }
}
