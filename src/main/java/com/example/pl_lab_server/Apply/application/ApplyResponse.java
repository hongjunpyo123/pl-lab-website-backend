package com.example.pl_lab_server.Apply.application;

import com.example.pl_lab_server.Apply.domain.entity.ApplyResponseEntity;
import com.example.pl_lab_server.Apply.domain.repository.ApplyResponseRepository;
import com.example.pl_lab_server.Apply.dto.ApplyResponseDto;
import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApplyResponse {
    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private ApplyResponseRepository applyResponseRepository;

    public ResponseEntity<?> AddResponse(ApplyResponseDto applyResponseDto){
        try {
            ApplyResponseEntity applyResponseEntity = applyResponseDto.toEntity();
            applyResponseEntity.setApplicantStdNo(securityUtil.SimpleEncrypt(applyResponseDto.getApplicantStdNo()));
            applyResponseEntity.setQuestionId(applyResponseDto.getQuestionId());
            applyResponseEntity.setApplicantResponse(securityUtil.encrypt(applyResponseDto.getApplicantResponse()));
            applyResponseRepository.save(applyResponseEntity);
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "응답이 기록되었습니다", null));
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "응답 기록에 실패하였습니다", null));
        }
    }
}
