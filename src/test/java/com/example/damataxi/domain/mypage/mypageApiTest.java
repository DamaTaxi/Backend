package com.example.damataxi.domain.mypage;

import com.example.damataxi.ApiTest;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class mypageApiTest extends ApiTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUser(){
        User user = User.builder()
                .gcn(1234)
                .username("user")
                .email("xxxxxxxx@gmail.com")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    public void clear(){
        userRepository.deleteAll();
    }

    @Test
    public void 정보_설정_테스트() throws Exception {
        // given
        String token = makeAccessToken(String.valueOf(1234));
        MypageRequest request = MypageRequest.builder()
                .tel("01001010101")
                .latitude(12.2312)
                .longitude(23.4343)
                .build();

        // when
        ResultActions resultActions = requestSetUser(request, token);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
        User user = userRepository.findById(1234).orElseThrow(()-> { throw  new UserNotFoundException("1234"); });
        Assertions.assertEquals(user.getGcn(), 1234);
        Assertions.assertEquals(user.getUsername(), "user");
        Assertions.assertEquals(user.getEmail(), "xxxxxxxx@gmail.com");
        Assertions.assertEquals(user.getTel(), "01001010101");
        Assertions.assertEquals(user.getLatitude(), 12.2312);
        Assertions.assertEquals(user.getLongitude(), 23.4343);
    }

    private ResultActions requestSetUser(MypageRequest request, String token) throws Exception {
        return requestMvc(patch("/mypage").header("AUTHORIZATION", "Bearer " + token), request);
    }

    @Test
    public void 정보_받아오기_테스트() throws Exception {
        // given
        User user = userRepository.findById(1234).orElseThrow(() -> { throw new UserNotFoundException("1234"); });
        user.setTel("01001010101");
        user.setLatitude(12.2312);
        user.setLongitude(23.4343);
        userRepository.save(user);
        String token = makeAccessToken(String.valueOf(1234));

        // when
        ResultActions resultActions = requestGetUser(token);

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("tel").value("01001010101"))
                .andExpect(jsonPath("email").value("xxxxxxxx@gmail.com"))
                .andExpect(jsonPath("latitude").value(12.2312))
                .andExpect(jsonPath("longitude").value(23.4343))
                .andExpect(jsonPath("potId").isEmpty())
                .andDo(print());

    }

    private ResultActions requestGetUser(String token) throws Exception {
        return requestMvc(get("/mypage").header("AUTHORIZATION", "Bearer " + token));
    }
}
