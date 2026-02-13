package com.packt.cardatabase;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint { // 인증 실패(401) 발생 시 어떻게 응답할지 결정하는 클래스 (Custom 401 Response Class)

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답은 JSON 형식으로
        // 에러 메시지 작성
        PrintWriter writer = response.getWriter();
        writer.println("Error: " + authException.getMessage());
    }
}
