package com.example.damataxi.domain.auth;

import com.example.damataxi.ServiceTest;
import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.auth.dto.request.AdminLoginRequest;
import com.example.damataxi.domain.auth.dto.request.TokenRefreshRequest;
import com.example.damataxi.domain.auth.dto.response.TokenResponse;
import com.example.damataxi.domain.auth.dto.response.UserTokenResponse;
import com.example.damataxi.domain.auth.retrofit.dto.DsmOauthResponse;
import com.example.damataxi.domain.auth.service.AccountProvider;
import com.example.damataxi.domain.auth.service.impl.AuthServiceImpl;
import com.example.damataxi.global.error.exception.InvalidTokenException;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import com.example.damataxi.global.security.JwtTokenProvider;
import com.example.damataxi.global.security.details.AdminDetails;
import com.example.damataxi.global.security.details.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class AuthServiceTest extends ServiceTest {

    @Mock
    private CustomUserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AccountProvider accountProvider;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void userLogin_테스트() {
        // given
        String token = "testToken";
        given(accountProvider.searchAccount(token)).willReturn(new DsmOauthResponse("가나다",1234,"xxxx@gmail.com"));
        given(jwtTokenProvider.generateAccessToken(1234)).willReturn("accessToken");
        given(jwtTokenProvider.generateRefreshToken(1234)).willReturn("refreshToken");

        // when
        UserTokenResponse response = authService.userLogin(token);

        // then
        Assertions.assertEquals(response.getAccessToken(), "accessToken");
        Assertions.assertEquals(response.getRefreshToken(), "refreshToken");
        Assertions.assertEquals(response.getFirstLogin(), true);
    }

    @Test
    public void adminLogin_테스트() {
        // given
        AdminLoginRequest request = new AdminLoginRequest("adminId","adminPassword");
        given(userDetailsService.loadUserByValue("adminId"))
                .willReturn(new AdminDetails(new Admin("adminId","adminPassword")));
        given(passwordEncoder.matches("adminPassword","adminPassword"))
                .willReturn(true);
        given(jwtTokenProvider.generateAccessToken("adminId")).willReturn("accessToken");
        given(jwtTokenProvider.generateRefreshToken("adminId")).willReturn("refreshToken");

        // when
        TokenResponse response = authService.adminLogin(request);

        // then
        Assertions.assertEquals(response.getAccessToken(), "accessToken");
        Assertions.assertEquals(response.getRefreshToken(), "refreshToken");
    }

    @Test
    public void adminLogin_UserNotFoundException_테스트() {
        // given
        AdminLoginRequest request = new AdminLoginRequest("adminId","inputAdminPassword");
        given(userDetailsService.loadUserByValue("adminId"))
                .willReturn(new AdminDetails(new Admin("adminId","originAdminPassword")));
        given(passwordEncoder.matches("inputAdminPassword","originAdminPassword"))
                .willReturn(false);

        // when, then
        assertThrows(UserNotFoundException.class, ()-> authService.adminLogin(request));
    }

    @Test
    public void tokenRequest_테스트() {
        //given
        TokenRefreshRequest request = new TokenRefreshRequest("refreshToken");
        given(jwtTokenProvider.validateRefreshToken("refreshToken")).willReturn(true);
        given(jwtTokenProvider.getId("refreshToken")).willReturn("id");
        given(jwtTokenProvider.generateAccessToken("id")).willReturn("accessToken");
        given(jwtTokenProvider.generateRefreshToken("id")).willReturn("refreshToken");
        //when
        TokenResponse response = authService.tokenRefresh(request);
        //then
        Assertions.assertEquals(response.getAccessToken(), "accessToken");
        Assertions.assertEquals(response.getRefreshToken(), "refreshToken");
    }

    @Test
    public void tokenRequest_InvalidTokenException_테스트() {
        //given
        TokenRefreshRequest request = new TokenRefreshRequest("refreshToken");
        given(jwtTokenProvider.validateRefreshToken("refreshToken")).willReturn(false);
        //when, then
        assertThrows(InvalidTokenException.class, ()-> authService.tokenRefresh(request));
    }
}

