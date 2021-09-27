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

                .antMatchers(HttpMethod.POST, "/login/test").permitAll()

                .antMatchers(ApiPath.SWAGGER_PATH.getApiPath()).permitAll()
                .antMatchers(ApiPath.LOGIN_PATH.getApiPath()).permitAll()
                .antMatchers(ApiPath.MYPAGE_PATH.getApiPath()).hasAuthority("USER")

                .antMatchers(HttpMethod.GET, ApiPath.TAXI_POT_GET_PATH.getApiPath()).permitAll()
                .antMatchers(HttpMethod.POST, ApiPath.TAXI_POT_POST_PATH.getApiPath()).hasAuthority("USER")
                .antMatchers(HttpMethod.PATCH, ApiPath.TAXI_POT_PATCH_PATH.getApiPath()).hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, ApiPath.TAXI_POT_DELETE_PATH.getApiPath()).hasAuthority("USER")

                .antMatchers(HttpMethod.POST, ApiPath.ERROR_REPORT_POST_PATH.getApiPath()).permitAll()
                .antMatchers(HttpMethod.GET, ApiPath.ERROR_REPORT_GET_PATH.getApiPath()).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, ApiPath.ERROR_REPORT_DELETE_PATH.getApiPath()).hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, ApiPath.SUGGESTION_POST_PATH.getApiPath()).permitAll()
                .antMatchers(HttpMethod.GET, ApiPath.SUGGESTION_GET_PATH.getApiPath()).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, ApiPath.SUGGESTION_DELETE_PATH.getApiPath()).hasAuthority("ADMIN")

                .anyRequest().authenticated()
                .and().apply(new JwtConfigure(jwtTokenProvider));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}