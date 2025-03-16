package com.example.pl_lab_server.admin.application;

import com.example.pl_lab_server.Common.auth.GlobalAuthenticationManager;
import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.MailUtil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.User.Dto.UserSignUpDto;
import com.example.pl_lab_server.User.domain.repository.UserRepository;
import com.example.pl_lab_server.admin.dto.AdminSignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        private UserRepository userRepository;

        @Autowired
        private BCryptPasswordEncoder hash;

        @Transactional
        public ResponseEntity<?> AdminSignUp(AdminSignUpDto adminSignUpDto){
            UserSignUpDto userSignUpDto = new UserSignUpDto();
            if(adminSignUpDto.getAuthCode().equals(GlobalAuthenticationManager.GlobalAuthentication.authCode)){

                if(userRepository.existsByUserEmail(adminSignUpDto.getUserEmail())){
                    userRepository.deleteByUserEmail(adminSignUpDto.getUserEmail());
                }

                userSignUpDto.setUserEmail(adminSignUpDto.getUserEmail());
                userSignUpDto.setUserImageURL(null);
                userSignUpDto.setUserPhone(securityUtil.encrypt(adminSignUpDto.getUserPhone()));
                userSignUpDto.setRegDt(securityUtil.SimpleEncrypt(baseUtil.getTodayANDtime()));
                userSignUpDto.setGradDt(null);
                userSignUpDto.setUsreName(securityUtil.encrypt(adminSignUpDto.getUserName()));
                userSignUpDto.setStdNo(securityUtil.SimpleEncrypt(adminSignUpDto.getStdNo()));
                userSignUpDto.setType(securityUtil.encrypt("ADMIN"));
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
            String token = request.getHeader("Authorization");
            token = token.substring(7);

            GlobalAuthenticationManager.GlobalAuthentication.authCode = baseUtil.getCode();
            mailUtil.sendEmail("hjphjp321212@gmail.com", "관리자 회원가입 요청"
                    , "인증코드 : " + GlobalAuthenticationManager.GlobalAuthentication.authCode + "\n"
                            + "요청자 이름: " + jwTutil.getUsername(token) + "\n"
                            + "요청자 이메일: " + jwTutil.getEmail(token));
            return ResponseEntity.ok().body(ResponseDto
                    .response(HttpStatus.OK, "인증 메일 전송 성공", null));
        }
    }

