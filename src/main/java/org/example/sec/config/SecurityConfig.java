package org.example.sec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig { // 이름은 상관 X
    // 원래 자연적으로 가지고 있던 건 비활성화되고 우리가 만든 걸 사용
    @Bean
    InMemoryUserDetailsManager userDetailService(
            SecurityProperty p,
            PasswordEncoder passwordEncoder
    ) {
        UserDetails admin = User.builder()
                .username(p.username())
//                .password(p.password())
                .password(passwordEncoder.encode(p.password()))
                .roles(p.role())
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        // 필터 역할을 하여 dispatcher servlet에 앞서서 보안 처리
        // http -> 빌더
        http
                // 접근
//            .authorizeHttpRequests(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth
                                // 위에서 만난 패턴이 먼저 처리되며, 그 이후를 진행하지 X
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/error/**").permitAll()
                                .requestMatchers("/free/1", "/free/2").permitAll()
                                .requestMatchers(HttpMethod.GET, "/free/3", "/free/3").permitAll()
                                // 모든 요청에 대해서 허락하겠다
                                // .anyRequest().permitAll()
                                // 모든 요청에 대해서 인가 요청을 하겠다
                                .anyRequest().authenticated()
                )
                // 로그인, 로그아웃
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());
        return http.build();
    }
}
