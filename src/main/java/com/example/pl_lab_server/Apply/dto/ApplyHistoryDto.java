package com.example.pl_lab_server.Apply.dto;

import com.example.pl_lab_server.Apply.domain.entity.ApplyHistoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyHistoryDto {
    private String applicantStdNo;
    private String applicantUserName;
    private String applicantRegDt;
    private String statusCode;

    public ApplyHistoryEntity toEntity(){
        ApplyHistoryEntity applyHistoryEntity = new ApplyHistoryEntity();
        applyHistoryEntity.setApplicantStdNo(this.applicantStdNo);
        applyHistoryEntity.setApplicantUserName(this.applicantUserName);
        applyHistoryEntity.setApplicantRegDt(this.applicantRegDt);
        applyHistoryEntity.setStatusCode(this.statusCode);
        return applyHistoryEntity;
    }
}
