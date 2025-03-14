package com.example.pl_lab_server.User.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    @Schema(example = "test123@gmail.com")
    private String userEmail;

    @Schema(example = "test123##1")
    private String userPw;

    @Schema(example = "홍길똥")
    private String userName;

    @Schema(example = "01012345678")
    private String userPhone;

    @Schema(hidden = true)
    private String userImageURL;
}
