package com.example.damataxi.global.security.details;

import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.AdminRepository;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService{

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public UserDetails loadUserByValue(String value) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findById(value);
        Optional<Admin> admin = adminRepository.findById(value);
        if(user.isPresent()) {
            return new CustomUserDetails(user.get());
        }
        else if(admin.isPresent()) {
            return new AdminDetails(admin.get());
        }
        throw new UsernameNotFoundException(value);

    }
}