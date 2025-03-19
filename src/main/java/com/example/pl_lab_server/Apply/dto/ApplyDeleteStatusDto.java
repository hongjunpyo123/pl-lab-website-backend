package com.example.pl_lab_server.Apply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyDeleteStatusDto {

    @Schema(example = "001")
    private String statusCode;

}
