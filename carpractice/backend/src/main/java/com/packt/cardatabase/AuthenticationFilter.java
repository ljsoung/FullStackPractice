package com.packt.cardatabase;

import com.packt.cardatabase.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter -> 요청 → 필터 → 컨트롤러 보안 관문 통과
    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // 사용자 요청 시 토큰 검증하기 (OncePerRequestFilter 사용하기 때문에 매 요청마다 검증
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토큰 검증 및 사용자 가져오기
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jws != null) { // 존재 시
            // 토큰 검증 및 사용자 가져오기
            String user = jwtService.getAuthUser(request);
            // 인증하기
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            // user -> principal(username)
            // null -> credentials (비밀번호 필요 없음)
            // Collections.emptyList() -> 권한 목록 (비어 있음)

            SecurityContextHolder.getContext().setAuthentication(authentication); // 이걸 해야 Spring Security는 로그인된 사용자라고 인식한다.
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }
}
