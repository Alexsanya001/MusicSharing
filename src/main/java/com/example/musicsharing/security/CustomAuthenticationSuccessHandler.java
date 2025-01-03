package com.example.musicsharing.security;

import com.example.musicsharing.models.dto.ApiResponse;
import com.example.musicsharing.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt-exp-time}")
    private Duration tokenExpTime;

    private static final String IDENTIFIER_PREFIX = "Username: %s";

    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final AttemptsLimitService attemptsLimitService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String token = generateToken(user);
        ApiResponse<String> responseBody = ApiResponse.success(token);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        response.getWriter().flush();

        attemptsLimitService.discardLoginAttempts(
                String.format(IDENTIFIER_PREFIX, user.getUsername())
        );
    }


    private String generateToken(CustomUserDetails user) {
        String userId = String.valueOf(user.getId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", user.getAuthorities().stream().findFirst().get().getAuthority());
        return jwtUtil.generateToken(userId, claims, tokenExpTime);
    }
}