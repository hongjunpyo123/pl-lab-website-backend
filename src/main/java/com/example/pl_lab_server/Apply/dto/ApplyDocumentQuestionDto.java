package com.example.pl_lab_server.Apply.dto;

import com.example.pl_lab_server.Apply.domain.entity.ApplyDocumentQuestionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyDocumentQuestionDto {

    @Schema(hidden = true)
    private Long questionId;

    @Schema(example = "나는 어떤 사람인가요? 100자 이내로 서술해주세요.")
    private String questionText;

    @Schema(example = "1. 지원자의 핵심적인 특성을 빠르게 파악하기 위함임 / 2. 지원자가 자신을 얼마나 잘 알고 있는지 확인하기 위함임")
    private String questionDescription;

    @Schema(hidden = true)
    private String questionRegDt;

    public ApplyDocumentQuestionEntity toEntity(){
        ApplyDocumentQuestionEntity applyDocumentQuestionEntity = new ApplyDocumentQuestionEntity();
        applyDocumentQuestionEntity.setQuestionText(this.questionText);
        applyDocumentQuestionEntity.setQuestionDescription(this.questionDescription);
        applyDocumentQuestionEntity.setQuestionRegDt(this.questionRegDt);

        return applyDocumentQuestionEntity;
    }
}
