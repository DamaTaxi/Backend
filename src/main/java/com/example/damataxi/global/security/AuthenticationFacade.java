package com.example.damataxi.global.security;

import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.global.security.details.AdminDetails;
import com.example.damataxi.global.security.details.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getUser() {
        Authentication auth = getAuthentication();
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }

    public Admin getAdmin() {
        Authentication auth = getAuthentication();
        return ((AdminDetails)auth.getPrincipal()).getAdmin();
    }
}
