package com.example.pl_lab_server.Apply.application;

import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.Apply.domain.entity.ApplyDocumentQuestionEntity;
import com.example.pl_lab_server.Apply.domain.repository.ApplyDocumentQuestionRepository;
import com.example.pl_lab_server.Apply.dto.ApplyDocumentQuestionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ApplyDocumentQuestion {
    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private ApplyDocumentQuestionRepository applyDocumentQuestionRepository;

    public ResponseEntity<?> AddQuestion(ApplyDocumentQuestionDto applyDocumentQuestionDto){
        try{
            applyDocumentQuestionDto.setQuestionRegDt(securityUtil.SimpleEncrypt(baseUtil.getTodayAndTime()));
            //질문 암호화
            applyDocumentQuestionDto.setQuestionText(securityUtil.encrypt(applyDocumentQuestionDto.getQuestionText()));
            //설명 암호화
            applyDocumentQuestionDto.setQuestionDescription(securityUtil.encrypt(applyDocumentQuestionDto.getQuestionDescription()));

            ApplyDocumentQuestionEntity applyDocumentQuestionEntity = applyDocumentQuestionRepository.save(applyDocumentQuestionDto.toEntity());
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "등록에 성공하였습니다.", null));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "등록에 실패하였습니다.", null));
        }
    }

    public ResponseEntity<?> FindAllQuestion(){
        try {
            List<ApplyDocumentQuestionEntity> applyDocumentQuestionEntityList = applyDocumentQuestionRepository.findAll();
            for (ApplyDocumentQuestionEntity entity : applyDocumentQuestionEntityList) {
                entity.setQuestionRegDt(securityUtil.SimpleDecrypt(entity.getQuestionRegDt()));
                entity.setQuestionText(securityUtil.decrypt(entity.getQuestionText()));
                entity.setQuestionDescription(securityUtil.decrypt(entity.getQuestionDescription()));
            }
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "조회에 성공하였습니다.", applyDocumentQuestionEntityList));
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "조회에 실패하였습니다.", null));
        }
    }
}
