package com.example.pl_lab_server.Common.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTutil {

    @Autowired
    SecurityUtil securityUtil;

    @Value("${encrypt.key}")String encryptKey;

    private SecretKey secretKey;

    public JWTutil(@Value("${spring.jwt.secret}")String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class), encryptKey);
    }

    public String getRole(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class), encryptKey);
    }

    public String getIp(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("ip", String.class), encryptKey);
    }

    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createToken(String username, String role, Long expiredms, String ip){
        return Jwts.builder()
                .claim("username", securityUtil.encrypt(username, encryptKey))
                .claim("role", securityUtil.encrypt(role, encryptKey))
                .claim("ip", securityUtil.encrypt(ip, encryptKey))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredms))
                .signWith(secretKey)
                .compact();
    }
}
