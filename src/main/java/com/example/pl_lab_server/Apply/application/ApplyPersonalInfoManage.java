package com.example.pl_lab_server.Apply.application;

import com.example.pl_lab_server.Apply.domain.repository.ApplyHistoryRepository;
import com.example.pl_lab_server.Apply.domain.repository.ApplyResponseRepository;
import com.example.pl_lab_server.Apply.dto.ApplyDeletePersonalInfoDto;
import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.*;
import com.example.pl_lab_server.Apply.domain.entity.ApplyPersonalInfoEntity;
import com.example.pl_lab_server.Apply.domain.repository.ApplyPersonalInfoRepository;
import com.example.pl_lab_server.Apply.dto.ApplyPersonalInfoDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@Service
@Slf4j
public class ApplyPersonalInfoManage {
    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private HtmlTemplateUtil htmlTemplateUtil;

    @Autowired
    private ApplyPersonalInfoRepository applyPersonalInfoRepository;

    @Autowired
    private ApplyHistoryRepository applyHistoryRepository;

    @Autowired
    private ApplyResponseRepository applyResponseRepository;

    @Autowired
    private ApplyHistory applyHistory;

    public ResponseEntity<?> AddPersonalInfo(ApplyPersonalInfoDto applyPersonalInfoDto){
        try{
            if(applyPersonalInfoRepository.existsByApplicantStdNo(securityUtil.SimpleEncrypt(applyPersonalInfoDto.getApplicantStdNo()))){
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "기 지원자 입니다.", null));
            } else {
                if(applyPersonalInfoDto.getApplicantStdNo().length() != 8 || applyPersonalInfoDto.getApplicantUserPhone().length() != 11){
                    return ResponseEntity.badRequest()
                            .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "다시 입력해주세요.", null));
                }
                ApplyPersonalInfoEntity applyPersonalInfoEntity = applyPersonalInfoDto.toEntity();
                applyPersonalInfoEntity.setApplicantStdNo(securityUtil.SimpleEncrypt(applyPersonalInfoDto.getApplicantStdNo()));
                applyPersonalInfoEntity.setApplicantUserEmail(securityUtil.encrypt(applyPersonalInfoDto.getApplicantUserEmail()));
                applyPersonalInfoEntity.setApplicantUserName(securityUtil.encrypt(applyPersonalInfoDto.getApplicantUserName()));
                applyPersonalInfoEntity.setApplicantGrade(securityUtil.encrypt(applyPersonalInfoDto.getApplicantGrade()));
                applyPersonalInfoEntity.setApplicantUserPhone(securityUtil.encrypt(applyPersonalInfoDto.getApplicantUserPhone()));

                applyPersonalInfoRepository.save(applyPersonalInfoEntity);
                applyHistory.AddApplyHistory(applyPersonalInfoDto);

                mailUtil.clearRecipients();
                mailUtil.addRecipient(applyPersonalInfoDto.getApplicantUserEmail());
                mailUtil.sendHtmlEmail(mailUtil.getRecipients(), "접수 완료 안내", htmlTemplateUtil.createApplicationCompletionEmailTemplate(applyPersonalInfoDto.getApplicantUserName()));


                return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.OK, "정보 등록을 완료하였습니다.", null));
            }

        } catch (Exception e){
            log.error(e.getMessage());
            /*
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.error("상세 오류 메시지: " + sw.toString());
            */
            return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.BAD_REQUEST, "정보 등록에 실패하였습니다.", null));
        }
    }

    @Transactional
    public ResponseEntity<?> DeletePersonalInfo(ApplyDeletePersonalInfoDto applyDeletePersonalInfoDto){
        try {
            if(applyPersonalInfoRepository.existsByApplicantStdNo(securityUtil.SimpleEncrypt(applyDeletePersonalInfoDto.getApplicantStdNo()))){
                applyPersonalInfoRepository.deleteByApplicantStdNo(securityUtil.SimpleEncrypt(applyDeletePersonalInfoDto.getApplicantStdNo()));
                applyHistoryRepository.deleteByApplicantStdNo(securityUtil.SimpleEncrypt(applyDeletePersonalInfoDto.getApplicantStdNo()));
                applyResponseRepository.deleteByApplicantStdNo(securityUtil.SimpleEncrypt(applyDeletePersonalInfoDto.getApplicantStdNo()));
                return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.OK, "삭제에 성공하였습니다.", null));
            } else {
                log.error("회원이 존재하지 않습니다.");
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "삭제에 실패하였습니다.", null));
            }

        } catch (Exception e){
            log.error("delete: /apply/personal 호출 에러:" + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "삭제에 실패하였습니다.", null));
        }
    }

    public ResponseEntity<?> GetPersonalInfo(String applicantStdNo){
        try {
            if(applyPersonalInfoRepository.existsByApplicantStdNo(securityUtil.SimpleEncrypt(applicantStdNo))){
                ApplyPersonalInfoEntity applyPersonalInfoEntity = applyPersonalInfoRepository.findByApplicantStdNo(securityUtil.SimpleEncrypt(applicantStdNo));
                applyPersonalInfoEntity.setApplicantStdNo(applicantStdNo);
                applyPersonalInfoEntity.setApplicantGrade(securityUtil.decrypt(applyPersonalInfoEntity.getApplicantGrade()));
                applyPersonalInfoEntity.setApplicantUserEmail(securityUtil.decrypt(applyPersonalInfoEntity.getApplicantUserEmail()));
                applyPersonalInfoEntity.setApplicantUserName(securityUtil.decrypt(applyPersonalInfoEntity.getApplicantUserName()));
                applyPersonalInfoEntity.setApplicantUserPhone(securityUtil.decrypt(applyPersonalInfoEntity.getApplicantUserPhone()));
                return ResponseEntity
                        .ok().body(ResponseDto.response(HttpStatus.OK, "조회에 성공하였습니다.", applyPersonalInfoEntity));
            } else {
                return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다", null));
            }
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "조회에 실패하였습니다", null));
        }
    }

}
