package com.example.pl_lab_server.Apply.dto;

import com.example.pl_lab_server.Apply.domain.entity.ApplyPersonalInfoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyPersonalInfoDto {

    @Schema(example = "20210000")
    private String applicantStdNo;

    @Schema(example = "pllab1004@gmail.com")
    private String applicantUserEmail;

    @Schema(example = "피엘랩")
    private String applicantUserName;

    @Schema(example = "3")
    private String applicantGrade;

    @Schema(example = "01012345678")
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
