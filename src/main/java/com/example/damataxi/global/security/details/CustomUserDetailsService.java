package com.example.damataxi.global.security.details;

import com.example.damataxi.domain.auth.domain.AdminRepository;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.global.error.exception.UserNotFoundException;
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
        try {
            Optional<User> user = userRepository.findById(Integer.parseInt(value));
            if (user.isPresent()) {
                return new CustomUserDetails(user.get());
            }
        } catch (Exception e) {
            return new AdminDetails(adminRepository.findById(value)
                    .orElseThrow(() -> new UserNotFoundException(value)));
        }
        throw new UsernameNotFoundException(value);
    }
}