package com.example.pl_lab_server.User.domain.entity;

import com.example.pl_lab_server.User.Dto.UserSignUpDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "유저")
public class UserEntity {
    @Id
    @Column(length = 255)
    private String userEmail;

    @Column(length = 255)
    private String userPw;

    @Column(length = 255)
    private String stdNo;

    @Column(length = 255)
    private String regDt;

    @Column(length = 255)
    private String gradDt;

    @Column(length = 255)
    private String usreName;

    @Column(length = 255)
    private String userPhone;

    @Column(length = 255)
    private String userImageURL;

    @Column(length = 255)
    private String type;


    public UserSignUpDto toDto(){
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setUserEmail(this.userEmail);
        userSignUpDto.setUserPw(this.userPw);
        userSignUpDto.setStdNo(this.stdNo);
        userSignUpDto.setRegDt(this.regDt);
        userSignUpDto.setGradDt(this.gradDt);
        userSignUpDto.setUsreName(this.usreName);
        userSignUpDto.setUserPhone(this.userPhone);
        userSignUpDto.setUserImageURL(this.userImageURL);

        return userSignUpDto;
    }
}
