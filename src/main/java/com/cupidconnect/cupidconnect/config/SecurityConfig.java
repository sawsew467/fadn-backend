package com.cupidconnect.cupidconnect.config;

import com.cupidconnect.cupidconnect.exceptions.CustomAccessDeniedHandler;
import com.cupidconnect.cupidconnect.exceptions.CustomAuthenticationEntryPoint;
import com.cupidconnect.cupidconnect.exceptions.OAuth2LoginSuccessHandler;
import com.cupidconnect.cupidconnect.filter.JwtAuthenticationFilter;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/api/v1/auth/login/**",
                                        "/api/v1/auth/register/**",
                                        "/home",
                                        "/profile",
                                        "/api/v1/send-mail-otp",
                                        "/api/v1/send-mail-with-attachment")
                                .permitAll()
                                .requestMatchers("/api/v1/users/**").hasAnyAuthority("ADMIN", "USER", "PREMIUM")
                                .requestMatchers("/api/v1/auth/get-me").hasAnyAuthority("ADMIN", "USER", "PREMIUM")
//                                .requestMatchers("/api/v1/users/**").hasAuthority("USER")
                                .anyRequest()
                                .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2LoginSuccessHandler())
                )
                .userDetailsService(userDetailsService)
                // lỗi 403 Forbidden: là bị cấm nếu như nhập sai username hoặc password mặc dù có token hợp lệ
                // 401 Unauthorized: là bị cấm vì token ko hợp lệ để gửi lên server
                .exceptionHandling(e -> e
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // !!!! CHƯA CẦN CHO DỰ ÁN CUPID !!!!
//                .logout(l -> {
//                    l.logoutUrl("/logout")
//                            .addLogoutHandler(customLogoutHandler)
//                            .logoutSuccessHandler(
//                                    (request, response, authentication) -> SecurityContextHolder.clearContext()
//                            );
//                })
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return urlBasedCorsConfigurationSource;
    }


}
