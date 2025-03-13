package com.example.pl_lab_server.User.controller;

import com.example.pl_lab_server.Common.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageUserController {
    @GetMapping("/main")
    public ResponseEntity<?> main(){
        return ResponseEntity.ok(ResponseDto.response(HttpStatus.OK, "인증성공", null));
    }
}
