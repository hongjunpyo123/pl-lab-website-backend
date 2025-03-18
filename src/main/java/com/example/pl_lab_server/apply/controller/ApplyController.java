package com.example.pl_lab_server.apply.controller;


import com.example.pl_lab_server.apply.application.ApplyDocumentQuestion;
import com.example.pl_lab_server.apply.application.ApplyPersonalInfoManage;
import com.example.pl_lab_server.apply.application.ApplyStatusManage;
import com.example.pl_lab_server.apply.dto.ApplyAddStatusDto;
import com.example.pl_lab_server.apply.dto.ApplyDeleteStatusDto;
import com.example.pl_lab_server.apply.dto.ApplyDocumentQuestionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "랩실 지원 API", description = "랩실 지원 api입니다.")
@RequestMapping("/apply")
@RestController
public class ApplyController {

    @Autowired
    private ApplyStatusManage applyStatusManage;

    @Autowired
    private ApplyPersonalInfoManage applyPersonalInfoManage;

    @Autowired
    private ApplyDocumentQuestion applyDocumentQuestion;

    @Operation(summary = "지원 상태 추가 API", description =
                    "/ 001: 합격 " +
                    "/ 002: 불합격" +
                    "/ 003: 보류" +
                    "/ 004: 심사중")
    @PostMapping("/status/add")
    public ResponseEntity<?> AddStatus(ApplyAddStatusDto applyAddStatusDto){
        return applyStatusManage.AddStatus(applyAddStatusDto);
    }

    @Operation(summary = "지원 상태 삭제 API", description = "지원 상태를 삭제하는 api입니다.")
    @DeleteMapping("/status/delete")
    public ResponseEntity<?> DeleteStatus(ApplyDeleteStatusDto applyDeleteStatusDto){
        return applyStatusManage.DeleteStatus(applyDeleteStatusDto);
    }

    @Operation(summary = "지원 상태 조회 API", description = "지원 상태를 조회하는 api입니다.")
    @GetMapping("/status/{statusCode}")
    public ResponseEntity<?> FindStatus(@PathVariable String statusCode){
        return applyStatusManage.FindStatus(statusCode);
    }

    @Operation(summary = "지원 상태(전부) 조회 API", description = "현재 존재하는 지원 상태를 전부 조회합니다.")
    @GetMapping("/status/all")
    public ResponseEntity<?> FindAllStatus(){
        return applyStatusManage.FindAllStatus();
    }

    @Operation(summary = "서류 질문 추가 api", description = "서류 질문을 추가하는 api입니다.")
    @PostMapping("/question/add")
    public ResponseEntity<?> AddQuestion(ApplyDocumentQuestionDto applyDocumentQuestionDto){
        return applyDocumentQuestion.AddQuestion(applyDocumentQuestionDto);
    }

    @Operation(summary = "서류 질문(전부) 조회 api", description = "서류 질문을 전부 조회하는 api입니다.")
    @GetMapping("/question/all")
    public ResponseEntity<?> FindAllQuestion(){
        return applyDocumentQuestion.FindAllQuestion();
    }

}
