package com.packt.cardatabase.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    // 1일(밀리초). 실제 운영 시에는 더 짧아야 함
    static final long EXPIRATIONTIME = 86400000;
    static final String PREFIX = "Bearer ";

    // 비밀키 생성. 시연 목적으로만 이용
    // 운영 환경에서는 애플리케이션 구성에서 읽어들여와야 함
    // JWT의 마지막 부분을 담당
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 서명된 JWT 토큰을 생성
    public String getToken(String username){
        String token = Jwts.builder()
                .setSubject(username) // PAYLOAD
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)) // PAYLOAD
                .signWith(key) // 서명 알고리즘 결정 + Signature 계산 준비
                .compact(); // 여기서 Header, Payload 생성 및 Signature 계산하여 최종 JWT 토큰 생성
        return token;
    }

    // 요청의 Authorization 헤더에서 토큰을 가져온 뒤
    // 토큰을 확인하고 사용자 이름을 가져옴
    // JWT 검증 단계
    public String getAuthUser(HttpServletRequest request){
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 1) 헤더 없으면 패스
        if (header == null) return null;

        // 2) Bearer 토큰이 아니면 패스
        if (!header.startsWith(PREFIX)) return null;

        // 3) 토큰만 추출
        // 헤더에서 토큰 가져오기 -> Authorization / 헤더 값 읽기 EX. Bearer eyJhbGciOiJIUzI1NiIs...
        String token = header.substring(PREFIX.length()).trim();

        if(token != null){ // 토큰이 아예 없으면 인증 불가
            String user = Jwts.parserBuilder() // JWT를 해석할 객체 생성
                    .setSigningKey(key) // key로 검증함, 외부에서 조작 시 Signature 불일치
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    // "Bearer " 제거 후 순수 JWT 문자열 전달
                    // 전달된 JWT를 파싱하면서 Signature 및 만료시간(exp) 검증 수행
                    // 검증 성공 시 Claims(Payload) 반환, 실패 시 예외 발생
                    .getBody()// Payload 반환
                    .getSubject(); // sub 값 가져오기 즉, username값 가져오기

            if(user != null){
                return user;
            }
        }
        return null;
    }
}
