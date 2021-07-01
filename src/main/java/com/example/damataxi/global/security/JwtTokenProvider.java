package com.example.damataxi.global.security;

import com.example.damataxi.global.error.exception.InvalidTokenException;
import com.example.damataxi.global.security.details.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailsService userDetailsService;

    @Value("${auth.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(String value) {
        return makingToken(value, "access", 7200L);
    }

    public String generateRefreshToken(String value) {
        return makingToken(value, "refresh", 172800L);
    }

    public boolean validateAccessToken(String token){
        return validateToken(token, "access");
    }

    public boolean validateRefreshToken(String token){
        return validateToken(token, "refresh");
    }

    public String getId(String token) {
        try {
            return Jwts.parser().setSigningKey(encodingSecretKey()).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(checkToken(token)) {
            return token.substring(7);
        }
        return null;
    }

    public Boolean checkToken(String token) {
        return token != null && token.startsWith("Bearer");
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByValue(getId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    private boolean validateToken(String token, String typeKind) {
        try {
            String type = Jwts.parser().setSigningKey(encodingSecretKey()).parseClaimsJws(token).getBody().get("type", String.class);
            return type.equals(typeKind);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private String makingToken(String value, String type, Long time){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + (time * 1000L)))
                .signWith(SignatureAlgorithm.HS512, encodingSecretKey())
                .setIssuedAt(new Date())
                .setSubject(value)
                .claim("type", type)
                .compact();
    }

    private String encodingSecretKey(){
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

}
