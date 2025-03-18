package com.example.pl_lab_server.admin.application;

import com.example.pl_lab_server.Common.auth.GlobalAuthenticationManager;
import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.*;
import com.example.pl_lab_server.User.Dto.UserSignUpDto;
import com.example.pl_lab_server.User.domain.entity.UserEntity;
import com.example.pl_lab_server.User.domain.repository.UserRepository;
import com.example.pl_lab_server.admin.dto.AdminSignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AdminAuthentication {

        @Autowired
        private BaseUtil baseUtil;

        @Autowired
        private JWTutil jwTutil;

        @Autowired
        private MailUtil mailUtil;

        @Autowired
        private SecurityUtil securityUtil;

        @Autowired
        private HtmlTemplateUtil htmlTemplateUtil;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BCryptPasswordEncoder hash;

        @Transactional
        public ResponseEntity<?> AdminSignUp(AdminSignUpDto adminSignUpDto){
            UserSignUpDto userSignUpDto = new UserSignUpDto();
            if(adminSignUpDto.getAuthCode().equals(GlobalAuthenticationManager.GlobalAuthentication.authCode)){

                if(userRepository.existsByUserEmail(adminSignUpDto.getUserEmail())){
                    UserEntity userEntity = userRepository.findByUserEmail(adminSignUpDto.getUserEmail());
                    adminSignUpDto.setUserImageURL(userEntity.getUserImageURL());
                } else {
                    userSignUpDto.setUserImageURL(null);
                }

                userSignUpDto.setUserEmail(adminSignUpDto.getUserEmail());
                userSignUpDto.setUserPhone(securityUtil.encrypt(adminSignUpDto.getUserPhone()));
                userSignUpDto.setRegDt(securityUtil.SimpleEncrypt(baseUtil.getTodayAndTime()));
                userSignUpDto.setGradDt(null);
                userSignUpDto.setUsreName(securityUtil.encrypt(adminSignUpDto.getUserName()));
                userSignUpDto.setStdNo(securityUtil.SimpleEncrypt(adminSignUpDto.getStdNo()));
                userSignUpDto.setType(securityUtil.SimpleEncrypt("ADMIN"));
                userSignUpDto.setUserPw(hash.encode(adminSignUpDto.getUserPw()));

                userRepository.save(userSignUpDto.toEntity());
                GlobalAuthenticationManager.GlobalAuthentication.authCode = null;
                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "어드민 회원가입이 완료되었습니다.", null));
            } else {
                GlobalAuthenticationManager.GlobalAuthentication.authCode = null;
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "인증코드가 일치하지 않습니다.", null));
            }

        }

        public ResponseEntity<?> AdminSignUpAuth(HttpServletRequest request){

            List<UserEntity> userEntityList = userRepository.findAllByType(securityUtil.SimpleEncrypt("ADMIN"));
            log.info(securityUtil.SimpleEncrypt("ADMIN"));
            if(userEntityList.isEmpty()){
                mailUtil.addRecipient("pllab1004@gmail.com");
            } else {
                for (UserEntity user : userEntityList) {
                    mailUtil.addRecipient(user.getUserEmail());
                }
            }

            GlobalAuthenticationManager.GlobalAuthentication.authCode = baseUtil.getCode();
            String token = request.getHeader("Authorization");
            token = token.substring(7);
            String htmlContent = htmlTemplateUtil.createHtmlEmailTemplate(
                    GlobalAuthenticationManager.GlobalAuthentication.authCode,
                    jwTutil.getUsername(token),
                    jwTutil.getEmail(token)
            );

            try {
                mailUtil.sendHtmlEmail(mailUtil.getRecipients(), "관리자 회원가입 요청", htmlContent);
            } catch (Exception e) {
                log.error(e.getMessage());
                return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, "메일 전송에 실패하였습니다.", null));
            }
            return ResponseEntity.ok().body(ResponseDto
                    .response(HttpStatus.OK, "인증 메일 전송 성공", null));
        }
    }

