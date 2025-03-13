package com.example.pl_lab_server.User.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    @Schema(example = "test123@gmail.com")
    private String userEmail;

    @Schema(example = "test123##12")
    private String userPw;

    @Schema(example = "홍길동")
    private String usreName;
}
