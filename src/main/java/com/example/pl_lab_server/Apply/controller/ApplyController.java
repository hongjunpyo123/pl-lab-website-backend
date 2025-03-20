package com.example.pl_lab_server.Apply.controller;


import com.example.pl_lab_server.Apply.application.*;
import com.example.pl_lab_server.Apply.dto.*;
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

    @Autowired
    private ApplyResponse applyResponse;

    @Autowired
    private ApplyHistory applyHistory;

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
    public ResponseEntity<?> AddQuestion(@RequestBody ApplyDocumentQuestionDto applyDocumentQuestionDto){
        return applyDocumentQuestion.AddQuestion(applyDocumentQuestionDto);
    }

    @Operation(summary = "서류 질문(전부) 조회 api", description = "서류 질문을 전부 조회하는 api입니다.")
    @GetMapping("/question/all")
    public ResponseEntity<?> FindAllQuestion(){
        return applyDocumentQuestion.FindAllQuestion();
    }

    @Operation(summary = "지원자 정보 등록 api", description = "지원자의 개인 정보를 등록하는 api입니다.")
    @PostMapping("/personal/add")
    public ResponseEntity<?> AddPersonalInfo(@RequestBody ApplyPersonalInfoDto applyPersonalInfoDto){
        return applyPersonalInfoManage.AddPersonalInfo(applyPersonalInfoDto);
    }

    @Operation(summary = "지원자 개인정보 삭제 api", description = "현재 지원한 지원자를 삭제합니다.")
    @DeleteMapping("/personal")
    public ResponseEntity<?> DeletePersonalInfo(@RequestBody ApplyDeletePersonalInfoDto applyDeletePersonalInfoDto){
        return applyPersonalInfoManage.DeletePersonalInfo(applyDeletePersonalInfoDto);
    }

    @Operation(summary = "지원자 개인정보 조회(특정) api", description = "특정 지원자의 개인정보를 조회하는 api입니다.")
    @GetMapping("/personal/{applicantStdNo}")
    public ResponseEntity<?> GetPersonalInfo(@PathVariable String applicantStdNo){
        return applyPersonalInfoManage.GetPersonalInfo(applicantStdNo);
    }

    @Operation(summary = "지원자 응답 등록 api", description = "지원자가 질문에 응답한 내용을 등록하는 api입니다.")
    @PostMapping("/response/add")
    public ResponseEntity<?> AddResponse(@RequestBody ApplyResponseDto applyResponseDto){
        return applyResponse.AddResponse(applyResponseDto);
    }

    @Operation(summary = "지원자 응답 조회(특정) api", description = "지원자의 답변을 조회합니다.")
    @GetMapping("/response/{applicantStdNo}")
    public ResponseEntity<?> GetResponse(@PathVariable String applicantStdNo){
        return applyResponse.GetResponse(applicantStdNo);
    }

    @Operation(summary = "지원 내역(전부) 조회 api 입니다.", description = "지원자의 지원 내역을 전부 조회하는 api입니다.")
    @GetMapping("/history/all")
    public ResponseEntity<?> FindAllHistory(){
        return applyHistory.FindAllHistory();
    }



}
