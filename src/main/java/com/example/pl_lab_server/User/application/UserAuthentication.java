package com.example.pl_lab_server.User.application;

import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.User.Dto.UserLoginDto;
import com.example.pl_lab_server.User.Dto.UserSignUpDto;
import com.example.pl_lab_server.User.domain.entity.UserEntity;
import com.example.pl_lab_server.User.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;

@Service
public class UserAuthentication {

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder hash;

    @Autowired
    private ResourceLoader resourceLoader; //파일 저장을 위한 리소스 로더

    Long expirationTime = 3600000L;
    private static final Logger log = LoggerFactory.getLogger(UserAuthentication.class);

    @Value("${encrypt.key}")String encryptKey;

    public ResponseEntity<?> UserLogin(UserLoginDto userLoginDto, HttpServletRequest request){
        if(userRepository.existsByUserEmail(userLoginDto.getUserEmail())){
            UserEntity userEntity = userRepository.findByUserEmail(userLoginDto.getUserEmail());
            if(hash.matches(userLoginDto.getUserPw(), userEntity.getUserPw())){
                return ResponseEntity
                        .ok(ResponseDto
                                .response(HttpStatus.OK, "로그인에 성공하였습니다", jwTutil.createToken(userLoginDto.getUsreName(), userEntity.getType(), this.expirationTime, securityUtil.getClientIpv4(request))));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다", null));
            }
        } else {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "조회된 유저가 없습니다", null));
        }
    }

    public ResponseEntity<?> UserSignUp(UserSignUpDto userSignUpDto, MultipartFile file){
        String filePath;
        log.info("UserSignUp 메서드 실행");
        if (file != null && !file.isEmpty()) //file이 존재할 경우 처리
        {
            try { //이미지파일 업로드 예외처리
                log.info("이미지 처리 로직 실행");
                String fileName = file.getOriginalFilename(); //파일 이름
                Resource resource = resourceLoader.getResource("classpath:static/images");
                String uploadDir = resource.getFile().getAbsolutePath();
                filePath = "/images/" + fileName;
                file.transferTo(new File(uploadDir + File.separator + fileName));

                userSignUpDto.setUserImageURL(filePath); //이미지 파일 경로 저장

            } catch (Exception e) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "이미지 저장에 실패하였습니다.", null));
            }
        } else{
            filePath = null; //이미지 파일이 없을 경우 null로 초기화
            userSignUpDto.setUserImageURL(filePath); //이미지 파일 경로 저장
        }


        try {
//            "ROLE_USER" - 일반 사용자
//            "ROLE_ADMIN" - 관리자
//            "ROLE_GUEST" - 게스트 사용자
            userSignUpDto.setRegDt(baseUtil.getTodayANDtime());
            userSignUpDto.setGradDt(null);
            userSignUpDto.setUserPw(hash.encode(userSignUpDto.getUserPw()));
            userSignUpDto.setType("ROLE_ADMIN");
            userRepository.save(userSignUpDto.toEntity());
            return ResponseEntity
                    .ok(ResponseDto.response(HttpStatus.OK, "회원가입에 성공하였습니다", null));


        } catch (Exception e) {
            log.error("error : " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "회원가입에 실패하였습니다.", null));
        }
    }
}
