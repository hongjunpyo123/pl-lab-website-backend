package com.example.pl_lab_server.User.application;

import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.User.Dto.UserDeleteDto;
import com.example.pl_lab_server.User.Dto.UserLoginDto;
import com.example.pl_lab_server.User.Dto.UserSignUpDto;
import com.example.pl_lab_server.User.Dto.UserUpdateDto;
import com.example.pl_lab_server.User.domain.entity.UserEntity;
import com.example.pl_lab_server.User.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                                .response(HttpStatus.OK, "로그인에 성공하였습니다", jwTutil
                                        .createToken(
                                                securityUtil.decrypt(userEntity.getUsreName()),
                                                securityUtil.decrypt(userEntity.getType()),
                                                this.expirationTime,
                                                securityUtil.getClientIpv4(request),
                                                userEntity.getUserEmail()

                                        )));
            } else {
                log.error("비밀번호가 일치하지 않습니다");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다.", null));
            }
        } else {
            log.error("조회된 유저가 없습니다");
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다.", null));
        }
    }

    public ResponseEntity<?> UserSignUp(UserSignUpDto userSignUpDto, MultipartFile file){
        String filePath;
        log.info("UserSignUp 메서드 실행");

        if(userRepository.existsByUserEmail(userSignUpDto.getUserEmail())){
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "이미 회원이 존재합니다.", null));
        }

        if (file != null && !file.isEmpty()) //file이 존재할 경우 처리
        {
            try { //이미지파일 업로드 예외처리
                log.info("이미지 처리 로직 실행");
                String fileName = file.getOriginalFilename(); //파일 이름
                Resource resource = resourceLoader.getResource("classpath:static/images");
                String uploadDir = resource.getFile().getAbsolutePath();
                filePath = "/images/" + fileName;
                file.transferTo(new File(uploadDir + File.separator + fileName));

                userSignUpDto.setUserImageURL(securityUtil.encrypt(filePath)); //이미지 파일 경로 암호화 저장

            } catch (Exception e) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "이미지 저장에 실패하였습니다.", null));
            }
        } else{
            filePath = null; //이미지 파일이 없을 경우 null로 초기화
            userSignUpDto.setUserImageURL(filePath);
        }


        try {
//            "ROLE_USER" - 일반 사용자
//            "ROLE_ADMIN" - 관리자
//            "ROLE_GUEST" - 게스트 사용자
            userSignUpDto.setUserPhone(securityUtil.encrypt(userSignUpDto.getUserPhone()));
            //높은수준 폰번호 암호화 후 저장
            userSignUpDto.setUsreName(securityUtil.encrypt(userSignUpDto.getUsreName()));
            //높은수준 이름 암호화 후 저장
            userSignUpDto.setStdNo(securityUtil.SimpleEncrypt(userSignUpDto.getStdNo()));
            //낮은수준 학번 암호화 후 저장
            userSignUpDto.setRegDt(securityUtil.SimpleEncrypt(baseUtil.getTodayANDtime()));
            //낮은수준 등록일 암호화 후 저장

            userSignUpDto.setGradDt(null);
            userSignUpDto.setUserPw(hash.encode(userSignUpDto.getUserPw()));
            userSignUpDto.setType(securityUtil.encrypt("USER")); //사용자 권한 암호화
            userRepository.save(userSignUpDto.toEntity());
            return ResponseEntity
                    .ok(ResponseDto.response(HttpStatus.OK, "회원가입에 성공하였습니다", null));


        } catch (Exception e) {
            log.error("error : " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "회원가입에 실패하였습니다.", null));
        }
    }

    public ResponseEntity<?> UserUpdate(UserUpdateDto userUpdateDto, MultipartFile file, HttpServletRequest request){
        String filePath;
        UserSignUpDto userSignUpDto = new UserSignUpDto();

        String token = request.getHeader("Authorization");
        token = token.substring(7); // "Bearer " 제거

        if(userRepository.existsByUserEmail(userUpdateDto.getUserEmail())){ //유저가 존재한다면

            //---jwt 검증 로직
            if(jwTutil.getEmail(token).equals(userUpdateDto.getUserEmail())){
                log.info("유효한 토큰입니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다", null));
            }

            userSignUpDto = userRepository.findByUserEmail(userUpdateDto.getUserEmail()).toDto();
            if (file != null && !file.isEmpty()) //file이 존재할 경우 처리
            {
                try { //이미지파일 업로드 예외처리
                    log.info("이미지 처리 로직 실행");
                    String fileName = file.getOriginalFilename(); //파일 이름
                    Resource resource = resourceLoader.getResource("classpath:static/images");
                    String uploadDir = resource.getFile().getAbsolutePath();
                    filePath = "/images/" + fileName;
                    file.transferTo(new File(uploadDir + File.separator + fileName));

                    userUpdateDto.setUserImageURL(securityUtil.encrypt(filePath)); //이미지 파일 경로 암호화 저장

                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "이미지 저장에 실패하였습니다.", null));
                }
            } else{
                filePath = null; //이미지 파일이 없을 경우 null로 초기화
                userUpdateDto.setUserImageURL(filePath);
            }

            try {
                userSignUpDto.setUserPw(hash.encode(userUpdateDto.getUserPw()));
                userSignUpDto.setUsreName(securityUtil.encrypt(userUpdateDto.getUserName()));
                userSignUpDto.setUserPhone(securityUtil.encrypt(userUpdateDto.getUserPhone()));
                userSignUpDto.setUserImageURL(userUpdateDto.getUserImageURL());
                userRepository.save(userSignUpDto.toEntity());
                return ResponseEntity
                        .ok(ResponseDto.response(HttpStatus.OK, "업데이트에 성공하였습니다", null));


            } catch (Exception e) {
                log.error("error : " + e.getMessage());
                return ResponseEntity.badRequest()
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "업데이트에 실패하였습니다.", null));
            }


        } else {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "조회된 유저가 없습니다", null));
        }
    }

    public ResponseEntity<?> UserCheck(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDto
                            .response(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다. 게스트 유저 토큰 발급", jwTutil
                                    .createToken(
                                            "Guest",
                                            "GUEST",
                                            this.expirationTime,
                                            securityUtil.getClientIpv4(request),
                                            "GUEST@GUEST.GUEST"
                                    )));
        } else { //토큰이 헤더에 존재할 경우
            token = token.substring(7); // "Bearer " 제거
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || !authentication.isAuthenticated()){ //인증되지 않은 토큰일 경우
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다", null));
            } else { //인증된 토큰일 경우
                return ResponseEntity.ok(ResponseDto.response(HttpStatus.OK, "인증이 완료되었습니다", jwTutil.getUsername(token)));
            }
        }
    }

    @Transactional
    public ResponseEntity<?> UserDelete(UserDeleteDto userDeleteDto){
        if(userRepository.existsByUserEmail(userDeleteDto.getUserEmail())){
            UserEntity userEntity = userRepository.findByUserEmail(userDeleteDto.getUserEmail());
            //비밀번호 검증 로직
            if(!hash.matches(userDeleteDto.getUserPw(), userEntity.getUserPw())){
                    return ResponseEntity.badRequest()
                            .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다", null));
            } else {
                userRepository.deleteByUserEmail(userDeleteDto.getUserEmail());
                return ResponseEntity
                        .ok(ResponseDto.response(HttpStatus.OK, "삭제에 성공하였습니다", userDeleteDto.getUserEmail()));
            }
        } else {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "조회된 유저가 없습니다", null));
        }
    }


}
