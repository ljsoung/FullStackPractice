package com.packt.cardatabase;

import com.packt.cardatabase.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity // 기본 웹 보호 구성 해제, 해당 클래스에서 자체 구성을 정의
public class SecurityConfig {

    // InMemoryUserDetailsManager -> 메모리(서버 RAM)에 사용자 정보를 저장해서 인증(Authentication)을 처리해주는 클래스 (테스트용, 껏다 키면 데이터 사라짐)
    /*@Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.builder().username("user").
                password(passwordEncoder().encode("password"))
                .roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }

     */

    private final UserDetailsServiceImpl  userDetailsService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /*
    /*
    1. JWT니까 csrf 끈다
    2. JWT니까 stateless
    3. 로그인만 열어둔다
    4. 나머지는 인증 필요
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.anyRequest().permitAll());
        /*
        http.csrf((csrf) -> csrf.disable()) // 1
                .cors(Customizer.withDefaults()) // CORS 활성화
                .sessionManagement((sessionManagement) -> sessionManagement.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 2
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.POST, // 3
                                "/login").permitAll().anyRequest().authenticated()) // 4
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // 기본 로그인 필터 전에 내 JWT 필터를 끼워 넣어라
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(exceptionHandler)); //Exception 터지면 이걸로 Handling하겠다.
         */
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    // CORS -> 다른 출처 요청을 허용할지 말지 서버가 결정하는 규칙
    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // CORS 정책을 등록하는 Bean
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 어떤 URL에 어떤 CORS 규칙을 적용할 건지 정하는 곳
        CorsConfiguration config = new CorsConfiguration(); // CORS 규칙을 담는 설정 객체
        config.setAllowedOrigins(Arrays.asList("*")); // 모든 출처 허용

        /*
        // 출처를 명시적으로 정의하려명 다음과 같은 방법으로
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
         */

        config.setAllowedMethods(Arrays.asList("*")); // GET, POST, PUT 등등... 모두 허용
        config.setAllowedHeaders(Arrays.asList("*")); // Authorization, Content-type 등등... 모든 요청 헤더 허용
        config.setAllowCredentials(false); // 자격 증명 허용 여부 (쿠키 허용 X, 세션 기반 인증 X)
        config.applyPermitDefaultValues(); // Spring 기본값 허용

        source.registerCorsConfiguration("/**", config); // 모든 경로에 적용
        return source;
    }
}
