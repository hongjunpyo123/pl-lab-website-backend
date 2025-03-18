package com.example.pl_lab_server.User.controller;

import com.example.pl_lab_server.User.Dto.UserDeleteDto;
import com.example.pl_lab_server.User.Dto.UserLoginDto;
import com.example.pl_lab_server.User.Dto.UserSignUpDto;
import com.example.pl_lab_server.User.Dto.UserUpdateDto;
import com.example.pl_lab_server.User.application.UserAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User API", description = "회원 인증 API")
@RequestMapping("/user")
@RestController
public class AuthUserController {

    @Autowired
    UserAuthentication userAuthentication;

    @Operation(summary = "로그인 api", description = "유저 정보를 조회하고 성공 시 JWT 토큰 반환 / 해당 api호출 시 로컬스토리지에 저장된 기존 jwt토큰을 삭제해야합니다")
    @PostMapping("/login")
    public ResponseEntity<?> UserLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request){
        return userAuthentication.UserLogin(userLoginDto, request);
    }

    @Operation(summary = "회원가입 api", description =
            "유저 정보를 등록후 성공 메시지 반환 " +
            "(이미지는 선택사항이며, 호출에는 GUEST 권한이 필요합니다)")
    @PostMapping(
            value = "/signup",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> UserSignUp(@RequestPart(value = "user") UserSignUpDto userSignUpDto
                                        ,@RequestPart(value = "image", required = false) MultipartFile file){
        return userAuthentication.UserSignUp(userSignUpDto, file);
    }

    @Operation(summary = "회원수정 api", description = "유저 정보를 수정합니다")
    @PutMapping(
            value = "/update",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> UserUpdate(@RequestPart(value = "user")UserUpdateDto userUpdateDto
            , @RequestPart(value = "image", required = false) MultipartFile file,
                                        HttpServletRequest request){
        return userAuthentication.UserUpdate(userUpdateDto, file, request);
    }

    @Operation(summary = "jwt 유효성 검증 api", description = "웹페이지 접속 시 호출되는 api입니다. jwt토큰이 없을 시 게스트 사용자로 인식하고 토큰을 발급합니다")
    @GetMapping("/check")
    public ResponseEntity<?> UserCheck(HttpServletRequest request){
        return userAuthentication.UserCheck(request);
    }


    @Operation(summary = "회원삭제 api", description = "유저 정보를 삭제합니다", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/delete")
    public ResponseEntity<?> UserDelete(@RequestBody UserDeleteDto userDeleteDto){
        return userAuthentication.UserDelete(userDeleteDto);
    }


}
