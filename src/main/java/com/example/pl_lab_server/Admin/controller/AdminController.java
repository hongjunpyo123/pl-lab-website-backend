package com.example.pl_lab_server.Admin.controller;

import com.example.pl_lab_server.Admin.application.AdminAuthentication;
import com.example.pl_lab_server.Admin.dto.AdminSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin API", description = "관리자 API")

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminAuthentication adminAuthentication;

    @Operation(summary = "회원가입 관리자 인증 api", description = "관리자 이메일 인증을 위한 api")
    @GetMapping("/signup/auth")
    public ResponseEntity<?> AdminSignUpAuth(HttpServletRequest request){
        return adminAuthentication.AdminSignUpAuth(request);
    }

    @Operation(summary = "어드민 회원가입 api", description = "회원가입을 위해서는 관리자 이메일 인증이 필요합니다")
    @PostMapping("/signup")
    public ResponseEntity<?> AdminSignUp(@RequestBody AdminSignUpDto adminSignUpDto){
        return adminAuthentication.AdminSignUp(adminSignUpDto);
    }


}
