package com.example.pl_lab_server.Common.config;

import com.example.pl_lab_server.Common.filter.LoginFilter;
import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
public class SecurityConfig {
    private final JWTutil jwtUtil;
    private final SecurityUtil securityUtil;
    private final BaseUtil baseUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTutil jwtUtil, SecurityUtil securityUtil, BaseUtil baseUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.securityUtil = securityUtil;
        this.baseUtil = baseUtil;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/user/login", "/user/check", "/apply/index",          "/apply/history/all", "/admin/apply/history", "/apply/suc", "/apply/personal", "/apply/personal/{applicantStdNo}", "/apply/response/{applicantStdNo}", "/apply/question/all").permitAll()
                        .requestMatchers("/user/update", "/user/delete", "/admin/signup", "/admin/signup/auth").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user/signup", "/apply/personal/add", "/apply/response/add").hasAnyRole("GUEST", "ADMIN")
                        .anyRequest().hasRole("ADMIN"))
                .addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, securityUtil, baseUtil), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
