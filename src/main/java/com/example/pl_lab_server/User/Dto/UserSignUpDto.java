package com.example.pl_lab_server.User.Dto;

import com.example.pl_lab_server.User.domain.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    @Schema(example = "test123@gmail.com")
    private String userEmail;

    @Schema(example = "test123##12")
    private String userPw;

    @Schema(example = "20250000")
    private String stdNo;

    @Schema(hidden = true)
    private String regDt;

    @Schema(hidden = true)
    private String gradDt;

    @Schema(example = "홍길동")
    private String usreName;

    @Schema(example = "01012345678")
    private String userPhone;

    @Schema(hidden = true)
    private String userImageURL;

    @Schema(hidden = true)
    private String type;

    public UserEntity toEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(this.userEmail);
        userEntity.setUserPw(this.userPw);
        userEntity.setStdNo(this.stdNo);
        userEntity.setRegDt(this.regDt);
        userEntity.setGradDt(this.gradDt);
        userEntity.setUsreName(this.usreName);
        userEntity.setUserPhone(this.userPhone);
        userEntity.setUserImageURL(this.userImageURL);
        userEntity.setType(this.type);
        return userEntity;
    }
}
