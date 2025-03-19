package com.example.pl_lab_server.Admin.dto;

import com.example.pl_lab_server.User.domain.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSignUpDto {
    @Schema(example = "admin@admin.admin")
    private String userEmail;

    @Schema(example = "admin")
    private String userPw;

    @Schema(example = "20250000")
    private String stdNo;

    @Schema(hidden = true)
    private String regDt;

    @Schema(hidden = true)
    private String gradDt;

    @Schema(example = "관리자")
    private String userName;

    @Schema(example = "01012345678")
    private String userPhone;

    @Schema(hidden = true)
    private String userImageURL;

    @Schema(hidden = true)
    private String type;

    @Schema(example = "00000000")
    private String authCode;

    public UserEntity toEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(this.userEmail);
        userEntity.setUserPw(this.userPw);
        userEntity.setStdNo(this.stdNo);
        userEntity.setRegDt(this.regDt);
        userEntity.setGradDt(this.gradDt);
        userEntity.setUsreName(this.userName);
        userEntity.setUserPhone(this.userPhone);
        userEntity.setUserImageURL(this.userImageURL);
        userEntity.setType(this.type);
        return userEntity;
    }
}
