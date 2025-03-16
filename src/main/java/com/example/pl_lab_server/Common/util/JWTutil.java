package com.example.pl_lab_server.Common.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTutil {

    @Autowired
    SecurityUtil securityUtil;

    private SecretKey secretKey;

    public JWTutil(@Value("${spring.jwt.secret}")String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(securityUtil.SimpleEncrypt("mn"), String.class));
    }

    public String getRole(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(securityUtil.SimpleEncrypt("rn"), String.class));
    }

    public String getIp(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(securityUtil.SimpleEncrypt("pn"), String.class));
    }

    public String getEmail(String token){
        return securityUtil.decrypt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(securityUtil.SimpleEncrypt("me"), String.class));
    }

    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createToken(String username, String role, Long expiredms, String ip, String email){
        return Jwts.builder()
                .claim(securityUtil.SimpleEncrypt("mn"), securityUtil.encrypt(username))
                .claim(securityUtil.SimpleEncrypt("rn"), securityUtil.encrypt("ROLE_"+role))
                .claim(securityUtil.SimpleEncrypt("pn"), securityUtil.encrypt(ip))
                .claim(securityUtil.SimpleEncrypt("me"), securityUtil.encrypt(email))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredms))
                .signWith(secretKey)
                .compact();
    }
}
