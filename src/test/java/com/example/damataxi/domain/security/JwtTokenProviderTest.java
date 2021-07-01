package com.example.damataxi.domain.security;

import com.example.damataxi.IntegrationTest;
import com.example.damataxi.global.error.exception.InvalidTokenException;
import com.example.damataxi.global.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtTokenProviderTest extends IntegrationTest {

    @Value("${auth.jwt.secret}")
    private String secretKey;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    public void makeToken(){
        accessToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 7200000L))
                .signWith(SignatureAlgorithm.HS512, encodingSecretKey())
                .setIssuedAt(new Date())
                .setSubject("xxxx@gmail.com")
                .claim("type", "access")
                .compact();
        refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 172800000L))
                .signWith(SignatureAlgorithm.HS512, encodingSecretKey())
                .setIssuedAt(new Date())
                .setSubject("xxxx@gmail.com")
                .claim("type", "refresh")
                .compact();
    }

    @Test
    public void generateAccessToken_테스트() {
        // when
        String userIdToken= jwtTokenProvider.generateAccessToken("xxxx@gmail.com");
        String adminIdToken = jwtTokenProvider.generateAccessToken("admin");

        // then
        Assertions.assertEquals(getBody(userIdToken).getSubject(),"xxxx@gmail.com");
        Assertions.assertEquals(getBody(adminIdToken).getSubject(),"admin");
        Assertions.assertEquals(getBody(userIdToken).get("type", String.class), "access");
    }

    @Test
    public void generateRefreshToken_테스트() {
        // when
        String userIdToken= jwtTokenProvider.generateRefreshToken("xxxx@gmail.com");
        String adminIdToken = jwtTokenProvider.generateRefreshToken("admin");

        // then
        Assertions.assertEquals(getBody(userIdToken).getSubject(),"xxxx@gmail.com");
        Assertions.assertEquals(getBody(adminIdToken).getSubject(),"admin");
        Assertions.assertEquals(getBody(userIdToken).get("type", String.class), "refresh");
    }

    @Test
    public void validateAccessToken_테스트() {
        // when, then
        Assertions.assertTrue(jwtTokenProvider.validateAccessToken(accessToken));
        Assertions.assertFalse(jwtTokenProvider.validateAccessToken(refreshToken));
    }

    @Test
    public void validateRefreshToken_테스트() {
        // when, then
        Assertions.assertTrue(jwtTokenProvider.validateRefreshToken(refreshToken));
        Assertions.assertFalse(jwtTokenProvider.validateRefreshToken(accessToken));
    }

    @Test
    public void validateToken_InvalidTokenException_테스트() {
        // when, then
        assertThrows(InvalidTokenException.class, ()-> jwtTokenProvider.validateAccessToken("토큰아님"));
        assertThrows(InvalidTokenException.class, ()-> jwtTokenProvider.validateRefreshToken("토큰아님"));
    }

    @Test
    public void getId_테스트() {
        // when
        String accessId = jwtTokenProvider.getId(accessToken);
        String refreshId = jwtTokenProvider.getId(accessToken);

        // then
        Assertions.assertEquals(accessId,"xxxx@gmail.com");
        Assertions.assertEquals(refreshId, "xxxx@gmail.com");
    }

    @Test
    public void getId_InvalidTokenException_테스트() {
        // when, then
        assertThrows(InvalidTokenException.class, ()-> jwtTokenProvider.getId("토큰아님"));
    }

    @Test
    public void checkToken_테스트() {
        // when
        Assertions.assertTrue(jwtTokenProvider.checkToken("Bearer " + accessToken));
        Assertions.assertFalse(jwtTokenProvider.checkToken("토큰아님"));
    }

    private Claims getBody(String token){
        return Jwts.parser().setSigningKey(encodingSecretKey()).parseClaimsJws(token).getBody();
    }

    private String encodingSecretKey(){
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
