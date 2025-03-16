package com.example.pl_lab_server.Common.filter;

import com.example.pl_lab_server.Common.response.ResponseDto;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
@Slf4j
public class LoginFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    private final JWTutil jwTutil;
    private final SecurityUtil securityUtil;
    private final BaseUtil baseUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTutil jwtUtil, SecurityUtil securityUtil, BaseUtil baseUtil){
        this.authenticationManager = authenticationManager;
        this.jwTutil = jwtUtil;
        this.securityUtil = securityUtil;
        this.baseUtil = baseUtil;
    }

    private void handleAuthenticationError(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "인증 실패");
        errorResponse.put("status", HttpStatus.UNAUTHORIZED);
        log.error("인증 실패: " + errorMessage);
        log.error("timestamp: " + new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        // JWT 토큰 검증 로직
        if (token == null || !token.startsWith("Bearer ")) {
            log.info("인증 거부, 토큰 없음, : " + securityUtil.getClientIpv4(request));
            filterChain.doFilter(request, response);
            return;
        }

        try {
            token = token.substring(7); // "Bearer " 제거
            String accessIp = securityUtil.getClientIpv4(request);
            String tokenIp = jwTutil.getIp(token);
            String username = jwTutil.getUsername(token);
            String role = jwTutil.getRole(token);

            if (jwTutil.isExpired(token)) {
                throw new BadCredentialsException("토큰 만료됨");
            }

            if (!accessIp.equals(tokenIp)) {
                throw new BadCredentialsException("유효하지 않은 IP 주소");
            }

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            User authenticatedUser = new User(username, "", authorities);

            // Spring Security 컨텍스트에 인증 정보 설정
            var authenticationToken = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("인증 성공: " + username + "/role: " + role + "/ip: " + accessIp);

        } catch (Exception e) {
            System.out.println("인증 실패: " + e.getMessage());
            handleAuthenticationError(response, e.getMessage());
            return;
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }
}
