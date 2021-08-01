package com.example.damataxi.domain.auth.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.auth.dto.request.AdminLoginRequest;
import com.example.damataxi.domain.auth.dto.request.TokenRefreshRequest;
import com.example.damataxi.domain.auth.dto.response.TokenResponse;
import com.example.damataxi.domain.auth.dto.response.UserTokenResponse;
import com.example.damataxi.domain.auth.retrofit.dto.DsmOauthResponse;
import com.example.damataxi.domain.auth.service.DsmOauthAccountProvider;
import com.example.damataxi.domain.auth.service.AuthService;
import com.example.damataxi.global.error.exception.InvalidTokenException;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import com.example.damataxi.global.security.JwtTokenProvider;
import com.example.damataxi.global.security.details.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final DsmOauthAccountProvider dsmOauthAccountProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserTokenResponse userLogin(String oauthAccessToken) {
        DsmOauthResponse response = dsmOauthAccountProvider.searchAccount(oauthAccessToken);

        boolean firstLogin = false;
        if(userRepository.findById(response.getEmail()).isEmpty()) {
            firstLogin = true;
            User user = User.builder()
                    .gcn(response.getGcn())
                    .username(response.getName())
                    .email(response.getEmail())
                    .build();
            userRepository.save(user);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(response.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(response.getEmail());
        return new UserTokenResponse(accessToken, refreshToken, firstLogin);
    }

    @Override
    public TokenResponse adminLogin(AdminLoginRequest adminLoginRequest) {
        String id = adminLoginRequest.getId();
        UserDetails adminDetails = userDetailsService.loadUserByValue(id);

        if(!passwordEncoder.matches(adminLoginRequest.getPassword(),adminDetails.getPassword())) {
            throw new UserNotFoundException(id);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(id);
        String refreshToken = jwtTokenProvider.generateRefreshToken(id);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public TokenResponse tokenRefresh(TokenRefreshRequest tokenRefreshRequest) {
        if (jwtTokenProvider.validateRefreshToken(tokenRefreshRequest.getRefreshToken())) {
            String id = jwtTokenProvider.getId(tokenRefreshRequest.getRefreshToken());

            String accessToken = jwtTokenProvider.generateAccessToken(id);
            String refreshToken = jwtTokenProvider.generateRefreshToken(id);
            return new TokenResponse(accessToken, refreshToken);
        } else {
            throw new InvalidTokenException();
        }
    }

}
