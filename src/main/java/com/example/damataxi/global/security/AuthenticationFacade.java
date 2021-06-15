package com.example.damataxi.global.security;

import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.global.security.details.AdminDetails;
import com.example.damataxi.global.security.details.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAdmin() {
        Authentication auth = getAuthentication();
        return ((UserDetails) auth.getPrincipal()).getPassword() != null;
    }

    public User getUser() {
        Authentication auth = getAuthentication();
        return ((CustomUserDetails)auth).getUser();
    }

    public Admin getAdmin() {
        Authentication auth = getAuthentication();
        return ((AdminDetails)auth).getAdmin();
    }
}
