package com.example.pl_lab_server.Apply.dto;

import com.example.pl_lab_server.Apply.domain.entity.ApplyStatusEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyAddStatusDto {

    @Schema(example = "001")
    private String statusCode;

    @Schema(example = "합격")
    private String statusDescription;

    public ApplyStatusEntity toEntity(){
        ApplyStatusEntity applyStatusEntity = new ApplyStatusEntity();
        applyStatusEntity.setStatusCode(this.getStatusCode());
        applyStatusEntity.setStatusDescription(this.statusDescription);

        return applyStatusEntity;
    }
}
