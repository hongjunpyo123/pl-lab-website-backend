package com.example.pl_lab_server.Apply.dto;

import com.example.pl_lab_server.Apply.domain.entity.ApplyResponseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyResponseDto {

    @Schema(hidden = true)
    private Long applicantId;

    @Schema(example = "20210000")
    private String applicantStdNo;

    @Schema(example = "7")
    private String questionId;

    @Schema(example = "저는 컴퓨터공학과에 재학중인 000 이라고 합니다.........")
    private String applicantResponse;

    public ApplyResponseEntity toEntity(){
        ApplyResponseEntity applyResponseEntity = new ApplyResponseEntity();
        applyResponseEntity.setApplicantStdNo(this.applicantStdNo);
        applyResponseEntity.setQuestionId(this.questionId);
        applyResponseEntity.setApplicantResponse(this.applicantResponse);
        return applyResponseEntity;
    }
}
