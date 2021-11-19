package com.example.damataxi.domain.auth.service.impl;

import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.AdminRepository;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.auth.dto.request.AdminLoginRequest;
import com.example.damataxi.domain.auth.dto.request.TokenRefreshRequest;
import com.example.damataxi.domain.auth.dto.response.UserTokenResponse;
import com.example.damataxi.domain.auth.service.AuthService;
import com.example.damataxi.domain.auth.util.api.client.CodeClient;
import com.example.damataxi.domain.auth.util.api.client.TokenClient;
import com.example.damataxi.domain.auth.util.api.dto.CodeReqeust;
import com.example.damataxi.domain.auth.util.api.dto.CodeResponse;
import com.example.damataxi.domain.auth.util.api.dto.TokenResponse;
import com.example.damataxi.global.error.exception.InvalidTokenException;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import com.example.damataxi.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final CodeClient codeClient;
    private final TokenClient tokenClient;

    @Value("${auth.client.secret}")
    private String client_secret;

    @Value("${auth.client.id}")
    private String client_id;

    @Override
    public UserTokenResponse userLogin(String code) {
        CodeResponse codeResponse = codeClient.getUserToken(new CodeReqeust(
           client_id, client_secret, code
        ));

        TokenResponse response = tokenClient.getUserInfo("Bearer" + codeResponse.getAccess_token());

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
    public com.example.damataxi.domain.auth.dto.response.TokenResponse adminLogin(AdminLoginRequest adminLoginRequest) {
        String id = adminLoginRequest.getId();
        Admin admin = adminRepository.findById(adminLoginRequest.getId())
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(adminLoginRequest.getPassword(),admin.getPassword())) {
            throw new UserNotFoundException(id);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(id);
        String refreshToken = jwtTokenProvider.generateRefreshToken(id);
        return new com.example.damataxi.domain.auth.dto.response.TokenResponse(accessToken, refreshToken);
    }

    @Override
    public com.example.damataxi.domain.auth.dto.response.TokenResponse tokenRefresh(TokenRefreshRequest tokenRefreshRequest) {
        if (jwtTokenProvider.validateRefreshToken(tokenRefreshRequest.getRefreshToken())) {
            String id = jwtTokenProvider.getId(tokenRefreshRequest.getRefreshToken());

            String accessToken = jwtTokenProvider.generateAccessToken(id);
            String refreshToken = jwtTokenProvider.generateRefreshToken(id);
            return new com.example.damataxi.domain.auth.dto.response.TokenResponse(accessToken, refreshToken);
        } else {
            throw new InvalidTokenException();
        }
    }

    @Override
    public com.example.damataxi.domain.auth.dto.response.TokenResponse getTestUserToken() {
        User user = userRepository.findById("201406psh@dsm.hs.kr")
                .orElseThrow(UserNotFoundException::new);
        String email = user.getEmail();

        String accessToken = jwtTokenProvider.generateAccessToken(email);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);
        return new com.example.damataxi.domain.auth.dto.response.TokenResponse(accessToken, refreshToken);
    }

}
