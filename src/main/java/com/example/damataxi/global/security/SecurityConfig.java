package com.example.damataxi.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .cors().disable()
                .formLogin().disable()
                .authorizeRequests()

                .antMatchers(ApiPath.SWAGGER_PATH).permitAll()
                .antMatchers(ApiPath.LOGIN_PATH).permitAll()
                .antMatchers(ApiPath.MYPAGE_PATH).hasAuthority("USER")

                .antMatchers(HttpMethod.GET, ApiPath.TAXI_POT_GET_PATH).permitAll()
                .antMatchers(HttpMethod.POST, ApiPath.TAXI_POT_POST_PATH).hasAuthority("USER")
                .antMatchers(HttpMethod.PATCH, ApiPath.TAXI_POT_PATCH_PATH).hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, ApiPath.TAXI_POT_DELETE_PATH).hasAuthority("USER")

                .antMatchers(HttpMethod.POST, ApiPath.ERROR_REPORT_POST_PATH).permitAll()
                .antMatchers(HttpMethod.GET, ApiPath.ERROR_REPORT_GET_PATH).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, ApiPath.ERROR_REPORT_DELETE_PATH).hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, ApiPath.SUGGESION_POST_PATH).permitAll()
                .antMatchers(HttpMethod.GET, ApiPath.SUGGESION_GET_PATH).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, ApiPath.SUGGESION_DELETE_PATH).hasAuthority("ADMIN")

                .anyRequest().authenticated()
                .and().apply(new JwtConfigure(jwtTokenProvider));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}