package com.example.damataxi.domain.mypage;

import com.example.damataxi.ApiTest;
import com.example.damataxi.DummyDataCreatService;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.mypage.dto.MyTaxiPotContentTestResponse;
import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.example.damataxi.domain.taxiPot.dto.TaxiPotContentTestResponse;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MypageApiTest extends ApiTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DummyDataCreatService dummyDataCreatService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setUser(){
        User user = User.builder()
                .email("xxxx@gmail.com")
                .gcn("1234")
                .username("user")
                .build();
        this.user = userRepository.save(user);
    }

    @AfterEach
    public void clear(){
        userRepository.deleteAll();
    }

    @Test
    public void ??????_??????_?????????() throws Exception {
        // given
        String token = makeAccessToken("xxxx@gmail.com");
        MypageRequest request = MypageRequest.builder()
                .tel("01001010101")
                .latitude(12.2312)
                .longitude(23.4343)
                .address("address")
                .build();

        // when
        ResultActions resultActions = requestSetUser(request, token);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
        User user = userRepository.findById("xxxx@gmail.com").orElseThrow(UserNotFoundException::new);
        Assertions.assertEquals(user.getGcn(), "1234");
        Assertions.assertEquals(user.getUsername(), "user");
        Assertions.assertEquals(user.getEmail(), "xxxx@gmail.com");
        Assertions.assertEquals(user.getTel(), "01001010101");
        Assertions.assertEquals(user.getLatitude(), 12.2312);
        Assertions.assertEquals(user.getLongitude(), 23.4343);
        Assertions.assertEquals(user.getAddress(), "address");
    }

    private ResultActions requestSetUser(MypageRequest request, String token) throws Exception {
        return requestMvc(patch("/mypage").header("AUTHORIZATION", "Bearer " + token), request);
    }

    @Test
    public void ??????_????????????_?????????() throws Exception {
        // given
        User user = userRepository.findById("xxxx@gmail.com").orElseThrow(UserNotFoundException::new);
        user.setTel("01001010101");
        user.setLatitude(12.2312);
        user.setLongitude(23.4343);
        user.setAddress("address");
        userRepository.save(user);
        String token = makeAccessToken("xxxx@gmail.com");

        // when
        ResultActions resultActions = requestGetUser(token);

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("gcn").value("1234"))
                .andExpect(jsonPath("name").value("user"))
                .andExpect(jsonPath("tel").value("01001010101"))
                .andExpect(jsonPath("email").value("xxxx@gmail.com"))
                .andExpect(jsonPath("address").value("address"))
                .andExpect(jsonPath("potId").isEmpty())
                .andDo(print());

    }

    private ResultActions requestGetUser(String token) throws Exception {
        return requestMvc(get("/mypage").header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void ?????????_?????????_????????????_?????????_??????() throws Exception {
        // given
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        String token = makeAccessToken(user.getEmail());

        // when
        ResultActions resultActions = requestGetUserTaxiPot(token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        MyTaxiPotContentTestResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<MyTaxiPotContentTestResponse>() {});

        Assertions.assertTrue(response.getCreator().isMe());

    }

    @Test
    public void ?????????_?????????_????????????_??????_??????() throws Exception {
        // given
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        User user2 = User.builder()
                .email("yyyy@gmail.com")
                .gcn("2222")
                .username("user2")
                .build();
        userRepository.save(user2);
        dummyDataCreatService.makeReservation(taxiPot, user2);

        String token = makeAccessToken(user2.getEmail());

        // when
        ResultActions resultActions = requestGetUserTaxiPot(token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        MyTaxiPotContentTestResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<MyTaxiPotContentTestResponse>() {});

        Assertions.assertFalse(response.getCreator().isMe());

    }

    @Test
    public void ?????????_?????????_????????????_TaxiPotNotFoundException() throws Exception {
        // given
        String token = makeAccessToken(user.getEmail());

        // when
        ResultActions resultActions = requestGetUserTaxiPot(token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());

    }

    private ResultActions requestGetUserTaxiPot(String token) throws Exception {
        return requestMvc(get("/mypage/taxi-pot").header("AUTHORIZATION", "Bearer " + token));
    }
}
