package com.example.pl_lab_server.Apply.application;

import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.Apply.domain.entity.ApplyStatusEntity;
import com.example.pl_lab_server.Apply.domain.repository.ApplyStatusRepository;
import com.example.pl_lab_server.Apply.dto.ApplyAddStatusDto;
import com.example.pl_lab_server.Apply.dto.ApplyDeleteStatusDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyStatusManage {

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private ApplyStatusRepository applyStatusRepository;

    public ResponseEntity<?> AddStatus(ApplyAddStatusDto applyAddStatusDto){

        try{
            if(applyStatusRepository.existsByStatusCode(applyAddStatusDto.getStatusCode())){
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "이미 존재하는 상태코드 입니다.", null));
            } else {
                applyStatusRepository.save(applyAddStatusDto.toEntity());
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "등록에 성공하였습니다.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "등록에 실패하였습니다.", e.getMessage()));
        }

    }

    @Transactional
    public ResponseEntity<?> DeleteStatus(ApplyDeleteStatusDto applyDeleteStatusDto){

        try{
            if(!applyStatusRepository.existsByStatusCode(applyDeleteStatusDto.getStatusCode())){
                List<ApplyStatusEntity> applyStatusEntityList = applyStatusRepository.findAll();
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "상태가 존재하지 않습니다.", applyStatusEntityList));
            } else {
                applyStatusRepository.deleteByStatusCode(applyDeleteStatusDto.getStatusCode());
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "삭제에 성공하였습니다.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "삭제에 실패하였습니다.", e.getMessage()));
        }
    }

    public ResponseEntity<?> FindStatus(String statusCode){
        try {
            ApplyStatusEntity applyStatusEntity = applyStatusRepository.findByStatusCode(statusCode);
            if(!applyStatusRepository.existsByStatusCode(statusCode)) {
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "조회된 상태가 없습니다", null));
            } else {
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "조회에 성공하였습니다.", applyStatusEntity));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "조회에 실패하였습니다..", e.getMessage()));
        }
    }

    public ResponseEntity<?> FindAllStatus(){
        try{
            List<ApplyStatusEntity> applyStatusEntityList = applyStatusRepository.findAll();
            if(applyStatusEntityList.isEmpty()){
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "저장된 상태가 없습니다.", null));
            } else {
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "조회에 성공하였습니다.", applyStatusEntityList));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "조회에 실패하였습니다..", e.getMessage()));
        }
    }

}
