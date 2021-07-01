package com.example.damataxi.domain.security;

import com.example.damataxi.ServiceTest;
import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.AdminRepository;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.global.security.details.AdminDetails;
import com.example.damataxi.global.security.details.CustomUserDetails;
import com.example.damataxi.global.security.details.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class CustomUserDetailsServiceTest extends ServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByValue_user_테스트() {
        //given
        User user = User.builder()
                .gcn("1234")
                .username("testUser")
                .email("xxxx@gmail.com")
                .build();
        given(userRepository.findById("xxxx@gmail.com")).willReturn(Optional.of(user));

        //when
        CustomUserDetails response = (CustomUserDetails) customUserDetailsService.loadUserByValue("xxxx@gmail.com");

        //then
        Assertions.assertEquals(response.getUsername(), "testUser");
        Assertions.assertNull(response.getPassword());
    }

    @Test
    public void loadUserByValue_admin_테스트() {
        //given
        Admin admin = new Admin("adminId", "adminPassword");
        given(adminRepository.findById("adminId")).willReturn(Optional.of(admin));

        //when
        AdminDetails response = (AdminDetails) customUserDetailsService.loadUserByValue("adminId");

        //then
        Assertions.assertEquals(response.getUsername(), "adminId");
        Assertions.assertEquals(response.getPassword(), "adminPassword");
    }
}
