package com.packt.cardatabase;

import com.packt.cardatabase.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /*
    1. JWT니까 csrf 끈다
    2. JWT니까 stateless
    3. 로그인만 열어둔다
    4. 나머지는 인증 필요
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()) // 1
                .sessionManagement((sessionManagement) -> sessionManagement.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 2
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.POST, // 3
                                "/login").permitAll().anyRequest().authenticated()) // 4
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // 기본 로그인 필터 전에 내 JWT 필터를 끼워 넣어라
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
}
