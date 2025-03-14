package com.example.pl_lab_server.Common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class SecurityUtil {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Value("${encrypt.key}")String key;

    LocalDate today = LocalDate.now();
    String todayANDtime = now.format(formatter);


    public String encrypt(String text) { //문자열 암호화
        try {
            // 키를 16바이트로 포맷팅 (AES-128용)
            key = String.format("%-16s", key).substring(0, 16);

            // 비밀 키 생성
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            // "AES/CBC/PKCS5Padding" 모드 사용 (ECB 대신 CBC 사용)
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // 랜덤 초기화 벡터(IV) 생성 - AES 블록 크기는 16바이트
            byte[] iv = new byte[16];
            new java.security.SecureRandom().nextBytes(iv);
            javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(iv);

            // IV와 함께 암호화 모드 초기화
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            // 암호화 수행
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            // IV와 암호화된 데이터를 합침
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            // URL 안전 Base64 인코딩
            return Base64.getUrlEncoder().withoutPadding().encodeToString(combined);

        } catch (Exception e) {
            e.printStackTrace(); // 실제 환경에서는 적절한 로깅 사용
            return "error";
        }
    }

    public String decrypt(String encryptedText) { //문자열 복호화
        try {
            // 키를 16바이트로 포맷팅
            key = String.format("%-16s", key).substring(0, 16);

            // 비밀 키 생성
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            // Base64 디코딩
            byte[] combined = Base64.getUrlDecoder().decode(encryptedText);

            // IV 추출 (처음 16바이트)
            byte[] iv = new byte[16];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(iv);

            // 암호화된 데이터 추출 (나머지 바이트)
            byte[] encrypted = new byte[combined.length - iv.length];
            System.arraycopy(combined, iv.length, encrypted, 0, encrypted.length);

            // "AES/CBC/PKCS5Padding" 모드로 복호화 객체 생성
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            // 복호화 수행
            byte[] decrypted = cipher.doFinal(encrypted);

            // 복호화된 바이트 배열을 문자열로 변환
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace(); // 실제 환경에서는 적절한 로깅 사용
            return "error";
        }
    }

    public String getClientIpv4(HttpServletRequest request) {
        String[] headerNames = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        String ip = null;
        // 여러 헤더에서 IP 확인
        for (String headerName : headerNames) {
            ip = request.getHeader(headerName);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // 쉼표로 구분된 경우 첫 번째 IP 사용
                ip = ip.split(",")[0].trim();
                break;
            }
        }
        // 헤더에서 IP를 찾지 못한 경우 getRemoteAddr() 사용
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        // IPv4 형식 검증
        if (ip != null && ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return ip;
        }
        // IPv4가 아닌 경우 로컬호스트 IP 반환
        return "127.0.0.1";
    }

    public String SimpleEncrypt(String text) {
        if (text == null) return null;
        final int KEY = 7;
        final byte[] XOR_MASK = {0x3A, 0x7B, 0x5E, 0x2D};

        StringBuilder encrypted = new StringBuilder();
        int xorIndex = 0;
        int position = 0;

        for (char c : text.toCharArray()) {
            int value = (int) c;
            value = value ^ XOR_MASK[xorIndex];
            xorIndex = (xorIndex + 1) % XOR_MASK.length;
            int positionShift = position % 5;
            position++;
            int shifted = ((value + KEY + positionShift) % 256);
            encrypted.append(String.format("%03d", shifted));
        }

        return encrypted.toString();
    }

    public String SimpleDecrypt(String encrypted) {
        if (encrypted == null) return null;
        final int KEY = 7;
        final byte[] XOR_MASK = {0x3A, 0x7B, 0x5E, 0x2D};

        StringBuilder decrypted = new StringBuilder();
        int xorIndex = 0;
        int position = 0;

        for (int i = 0; i < encrypted.length(); i += 3) {
            if (i + 3 > encrypted.length()) break;

            int num = Integer.parseInt(encrypted.substring(i, i + 3));
            int positionShift = position % 5;
            position++;
            int value = ((num - KEY - positionShift + 256) % 256);
            value = value ^ XOR_MASK[xorIndex];
            xorIndex = (xorIndex + 1) % XOR_MASK.length;

            char c = (char) value;
            decrypted.append(c);
        }

        return decrypted.toString();
    }


}
