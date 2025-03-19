package com.example.pl_lab_server.Apply.application;

import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.Apply.domain.entity.ApplyHistoryEntity;
import com.example.pl_lab_server.Apply.domain.repository.ApplyHistoryRepository;
import com.example.pl_lab_server.Apply.dto.ApplyPersonalInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApplyHistory {
    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private ApplyHistoryRepository applyHistoryRepository;

    public ResponseEntity<?> AddApplyHistory(ApplyPersonalInfoDto applyPersonalInfoDto){
        try{
            ApplyHistoryEntity applyHistoryEntity = new ApplyHistoryEntity();
            applyHistoryEntity.setApplicantStdNo(securityUtil.SimpleEncrypt(applyPersonalInfoDto.getApplicantStdNo()));
            applyHistoryEntity.setApplicantUserName(securityUtil.encrypt(applyPersonalInfoDto.getApplicantUserName()));
            applyHistoryEntity.setApplicantRegDt(securityUtil.SimpleEncrypt(baseUtil.getTodayAndTime()));
            applyHistoryEntity.setStatusCode(securityUtil.encrypt("004"));
            applyHistoryRepository.save(applyHistoryEntity);
            return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.OK, "내역 저장 성공", null));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, "내역 저장 실패", null));
        }
    }

    public ResponseEntity<?> FindAllHistory(){
        try{
            List<ApplyHistoryEntity> applyHistoryEntityList = applyHistoryRepository.findAll();
            if(applyHistoryEntityList.isEmpty()){
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "지원 이력이 없습니다", null));
            } else {
                for (ApplyHistoryEntity entity : applyHistoryEntityList) {
                    entity.setApplicantStdNo(securityUtil.SimpleDecrypt(entity.getApplicantStdNo()));
                    entity.setApplicantUserName(securityUtil.decrypt(entity.getApplicantUserName()));
                    entity.setApplicantRegDt(securityUtil.SimpleDecrypt(entity.getApplicantRegDt()));
                    entity.setStatusCode(securityUtil.decrypt(entity.getStatusCode()));
                }
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "조회 성공", applyHistoryEntityList));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "알 수 없는 오류가 발생했습니다.", null));
        }

    }
}
