package com.example.damataxi.domain.taxiPot;

import com.example.damataxi.ApiTest;
import com.example.damataxi.DummyDataCreatService;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.dto.TaxiPotListContentTestResponse;
import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotListContentResponse;
import com.example.damataxi.global.error.exception.TaxiPotNotFoundException;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaxiPotApiTest extends ApiTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaxiPotRepository taxiPotRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private DummyDataCreatService dummyDataCreatService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clear() {
        reservationRepository.deleteAll();
        taxiPotRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 택시팟_현황_테스트() throws Exception {
        // given
        User user1 = dummyDataCreatService.makeUser(1234);
        User user2 = dummyDataCreatService.makeUser(2345);
        User user3 = dummyDataCreatService.makeUser(3456);
        User user4 = dummyDataCreatService.makeUser(4567);
        User user5 = dummyDataCreatService.makeUser(5678);
        TaxiPot taxiPot1 = dummyDataCreatService.makeTaxiPot(user1);
        TaxiPot taxiPot2 = dummyDataCreatService.makeTaxiPot(user2);
        dummyDataCreatService.makeReservation(taxiPot1, user1);
        dummyDataCreatService.makeReservation(taxiPot2, user2);
        dummyDataCreatService.makeReservation(taxiPot1, user3);
        dummyDataCreatService.makeReservation(taxiPot1, user4);
        dummyDataCreatService.makeReservation(taxiPot1, user5);
        // when
        ResultActions resultActions = requestGetTaxiPotInfo();
        // then
         resultActions.andExpect(status().isOk())
                 .andExpect(jsonPath("all").value(2))
                 .andExpect(jsonPath("reserve").value(1))
                .andDo(print())
                .andReturn();

    }

    private ResultActions requestGetTaxiPotInfo() throws Exception {
        return requestMvc(get("/taxi-pot/info"));
    }

    @Test
    public void 택시팟_리스트_받아오기_토큰_없음_테스트() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot1 =dummyDataCreatService.makeTaxiPot(creator1);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator2 = dummyDataCreatService.makeUser(2345);
        TaxiPot taxiPot2 =dummyDataCreatService.makeTaxiPot(creator2);
        dummyDataCreatService.makeReservation(taxiPot2, creator2);

        User creator3 = dummyDataCreatService.makeUser(3456);
        TaxiPot taxiPot3 =dummyDataCreatService.makeTaxiPot(creator3);
        dummyDataCreatService.makeReservation(taxiPot3, creator3);

        User creator4 = dummyDataCreatService.makeUser(3210);
        TaxiPot taxiPot4 =dummyDataCreatService.makeTaxiPot(creator4);
        dummyDataCreatService.makeReservation(taxiPot4, creator4);

        // when
        ResultActions resultActions = requestGetTaxiPotList(3, 0);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<TaxiPotListContentTestResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<TaxiPotListContentTestResponse>>() {});

        Assertions.assertEquals(response.size(), 3);

    }

    private ResultActions requestGetTaxiPotList(int size, int page) throws Exception {
        return requestMvc(get("/taxi-pot?size=" + size + "&page=" + page));
    }

    @Test
    public void 택시팟_리스트_받아오기_토큰있음_target확인_테스트() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot1 =dummyDataCreatService.makeTaxiPot(creator1);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator3 = dummyDataCreatService.makeUser(2222);
        TaxiPot taxiPot3 = dummyDataCreatService.makeTaxiPot(creator3, TaxiPotTarget.SOPHOMORE);
        dummyDataCreatService.makeReservation(taxiPot3, creator3);

        User creator4 = dummyDataCreatService.makeUser(1111);
        TaxiPot taxiPot4 = dummyDataCreatService.makeTaxiPot(creator4, TaxiPotTarget.FRESHMAN);
        dummyDataCreatService.makeReservation(taxiPot4, creator4);

        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        // when
        ResultActions resultActions = requestGetTaxiPotList(3, 0, token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<TaxiPotListContentTestResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<TaxiPotListContentTestResponse>>() {});

        Assertions.assertEquals(response.size(), 2);
    }

    @Test
    public void 택시팟_리스트_받아오기_토큰있음_거리확인_테스트() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser(1111);
        TaxiPot taxiPot1 = dummyDataCreatService.makeTaxiPot(creator1, 1.0000, 1.0000);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator2 = dummyDataCreatService.makeUser(2222);
        TaxiPot taxiPot2 = dummyDataCreatService.makeTaxiPot(creator2, 2.0000, 2.0000);
        dummyDataCreatService.makeReservation(taxiPot2, creator2);

        User user = dummyDataCreatService.makeUser(3333);
        user.setLatitude(3.0000);
        user.setLongitude(3.0000);
        userRepository.save(user);
        String token = makeAccessToken(String.valueOf(user.getGcn()));

        // when
        ResultActions resultActions = requestGetTaxiPotList(3, 0, token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<TaxiPotListContentTestResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<TaxiPotListContentTestResponse>>() {});

        Assertions.assertEquals(response.size(), 2);
        Assertions.assertEquals(response.get(0).getLatitude(), 2.0000);
        Assertions.assertEquals(response.get(0).getLongitude(), 2.0000);
        Assertions.assertEquals(response.get(1).getLatitude(), 1.0000);
        Assertions.assertEquals(response.get(1).getLongitude(), 1.0000);

    }

    private ResultActions requestGetTaxiPotList(int size, int page, String token) throws Exception {
        return requestMvc(get("/taxi-pot?size=" + size + "&page=" + page).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 택시팟_내용_받아오기_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        Reservation reservation = dummyDataCreatService.makeReservation(taxiPot, user);
        // when
        ResultActions resultActions = requestGetTaxiPotContent(taxiPot.getId());
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("creator").value(taxiPot.getCreator().getUsername()))
                .andExpect(jsonPath("target").value(taxiPot.getTarget().name()))
                .andExpect(jsonPath("price").value(taxiPot.getPrice()))
                .andExpect(jsonPath("reserve").value(taxiPot.getReservations().size()))
                .andExpect(jsonPath("all").value(taxiPot.getAmount()))
                .andExpect(jsonPath("latitude").value(taxiPot.getDestinationLatitude()))
                .andExpect(jsonPath("longitude").value(taxiPot.getDestinationLongitude()))
                .andExpect(jsonPath("place").value(taxiPot.getPlace()))
                .andExpect(jsonPath("content").value(taxiPot.getContent()))
                .andDo(print())
                .andReturn();
    }

    private ResultActions requestGetTaxiPotContent(int id) throws Exception {
        return requestMvc(get("/taxi-pot/" + id));
    }

    @Test
    public void 택시팟_만들기_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(1234));
        TaxiPotContentRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestMakeTaxiPot(request, token);

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
        Reservation reservation = reservationRepository.findByIdUserGcn(1234)
                .orElseThrow(()-> { throw new UserNotFoundException(1234); });
        TaxiPot taxiPot = reservation.getTaxiPot();

        Assertions.assertEquals(taxiPot.getCreator().getGcn(), 1234);
        Assertions.assertEquals(taxiPot.getTarget(), TaxiPotTarget.ALL);
        Assertions.assertEquals(taxiPot.getPlace(), request.getPlace());
        Assertions.assertEquals(taxiPot.getDestinationLatitude(), request.getLatitude());
        Assertions.assertEquals(taxiPot.getDestinationLongitude(), request.getLongitude());
        Assertions.assertEquals(taxiPot.getAmount(), request.getAmount());
        Assertions.assertEquals(taxiPot.getPrice(), request.getPrice());
        Assertions.assertNull(taxiPot.getContent());
    }

    @Test
    public void 택시팟_만들기_AlreadyApplyException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        String token = makeAccessToken(String.valueOf(1234));

        TaxiPotContentRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestMakeTaxiPot(request, token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_만들기_InvalidInputValueException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        TaxiPotContentRequest request = makeTaxiPotContentRequest("SENIOR");

        // when
        ResultActions resultActions = requestMakeTaxiPot(request, token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private TaxiPotContentRequest makeTaxiPotContentRequest(String target) {
        return TaxiPotContentRequest.builder()
                .target(target)
                .meetingAt(LocalDateTime.now())
                .place("기숙사 322호")
                .latitude(12.3456)
                .longitude(23.4567)
                .amount(3)
                .price(1000)
                .build();
    }

    private ResultActions requestMakeTaxiPot(TaxiPotContentRequest request, String token) throws Exception {
        return requestMvc(post("/taxi-pot").header("AUTHORIZATION", "Bearer " + token), request);
    }

    @Test
    public void 택시팟_수정_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        TaxiPotContentRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        TaxiPot patchedTaxiPot = taxiPotRepository.findById(taxiPot.getId())
                .orElseThrow(()-> { throw new TaxiPotNotFoundException(taxiPot.getId()); });

        Assertions.assertEquals(patchedTaxiPot.getCreator().getGcn(), 1234);
        Assertions.assertEquals(patchedTaxiPot.getTarget(), TaxiPotTarget.ALL);
        Assertions.assertEquals(patchedTaxiPot.getPlace(), request.getPlace());
        Assertions.assertEquals(patchedTaxiPot.getDestinationLatitude(), request.getLatitude());
        Assertions.assertEquals(patchedTaxiPot.getDestinationLongitude(), request.getLongitude());
        Assertions.assertEquals(patchedTaxiPot.getAmount(), request.getAmount());
        Assertions.assertEquals(patchedTaxiPot.getPrice(), request.getPrice());
        Assertions.assertNull(patchedTaxiPot.getContent());
    }

    @Test
    public void 택시팟_수정_NotCreatorException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));

        User fakeUser = dummyDataCreatService.makeUser(2345);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(fakeUser);
        dummyDataCreatService.makeReservation(taxiPot, fakeUser);
        TaxiPotContentRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_수정_ImpossibleChangeException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        User reservedUser = dummyDataCreatService.makeUser(2345);
        dummyDataCreatService.makeReservation(taxiPot, reservedUser);
        TaxiPotContentRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_수정_InvalidInputValueException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        TaxiPotContentRequest request = makeTaxiPotContentRequest("SENIOR");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestPatchTaxiPot(TaxiPotContentRequest request, int id, String token) throws Exception {
        return requestMvc(patch("/taxi-pot/" + id).header("AUTHORIZATION", "Bearer " + token), request);
    }

    @Test
    public void 택시팟_삭제_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        Reservation reservation = dummyDataCreatService.makeReservation(taxiPot, user);

        // when
        ResultActions resultActions = requestDeleteTaxiPot(taxiPot.getId(), token);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        User changedUser = userRepository.findById(user.getGcn())
                .orElseThrow(()-> { throw new UserNotFoundException(user.getUsername()); });

        Assertions.assertNull(changedUser.getReservation());
        Assertions.assertEquals(taxiPotRepository.findById(taxiPot.getId()), Optional.empty());
        Assertions.assertEquals(reservationRepository.findById(reservation.getId()), Optional.empty());
    }

    @Test
    public void 택시팟_삭제_NotCreatorException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        User fakeUser = dummyDataCreatService.makeUser(2345);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(fakeUser);
        dummyDataCreatService.makeReservation(taxiPot, fakeUser);

        // when
        ResultActions resultActions = requestDeleteTaxiPot(taxiPot.getId(), token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_삭제_ImpossibleChangeException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        User reservedUser = dummyDataCreatService.makeUser(2345);
        dummyDataCreatService.makeReservation(taxiPot, reservedUser);

        // when
        ResultActions resultActions = requestDeleteTaxiPot(taxiPot.getId(), token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestDeleteTaxiPot(int id, String token) throws Exception {
        return requestMvc(delete("/taxi-pot/" + id).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 택시팟_신청_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        User creator = dummyDataCreatService.makeUser(2345);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);
        // when
        ResultActions resultActions = requestApplyTaxiPot(taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
        User reservedUser = userRepository.findById(user.getGcn())
                .orElseThrow(()-> { throw new UserNotFoundException(user.getUsername()); });
        Assertions.assertNotNull(reservedUser.getReservation());
        Assertions.assertTrue(reservationRepository.findByIdUserGcn(user.getGcn()).isPresent());
    }

    @Test
    public void 택시팟_신청_AlreadyApplyException_테스트() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser(2345);
        String token = makeAccessToken(String.valueOf(creator.getGcn()));
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);
        // when
        ResultActions resultActions = requestApplyTaxiPot(taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_신청_TaxiPotFinishedReservationException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        String token = makeAccessToken(String.valueOf(user.getGcn()));

        User creator = dummyDataCreatService.makeUser(2345);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user1 = dummyDataCreatService.makeUser(1111);
        User user2 = dummyDataCreatService.makeUser(2222);
        User user3 = dummyDataCreatService.makeUser(3333);
        dummyDataCreatService.makeReservation(taxiPot, user1);
        dummyDataCreatService.makeReservation(taxiPot, user2);
        dummyDataCreatService.makeReservation(taxiPot, user3);
        // when
        ResultActions resultActions = requestApplyTaxiPot(taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_신청_TaxiPotNotFoundException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(2345);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        // when
        ResultActions resultActions = requestApplyTaxiPot(1, token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_신청_InvalidInputValueException_테스트() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser(3456);
        String token = makeAccessToken(String.valueOf(user.getGcn()));

        User creator = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user, TaxiPotTarget.FRESHMAN);
        dummyDataCreatService.makeReservation(taxiPot, creator);
        // when
        ResultActions resultActions = requestApplyTaxiPot(taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestApplyTaxiPot(int id, String token) throws Exception {
        return requestMvc(post("/taxi-pot/sub/" + id).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 택시팟_신청_취소_테스트() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user = dummyDataCreatService.makeUser(2345);
        dummyDataCreatService.makeReservation(taxiPot, user);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        // when
        ResultActions resultActions = requestCancleApplyTaxiPot(taxiPot.getId(), token);
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        User refreshUser = userRepository.findById(user.getGcn())
                .orElseThrow(()-> { throw new UserNotFoundException(user.getUsername()); });

        Assertions.assertNull(refreshUser.getReservation());
        Assertions.assertFalse(reservationRepository.findByIdUserGcn(user.getGcn()).isPresent());
    }

    @Test
    public void 택시팟_신청_취소_TaxiPotNotFoundException_테스트() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user = dummyDataCreatService.makeUser(2345);
        dummyDataCreatService.makeReservation(taxiPot, user);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        // when
        ResultActions resultActions = requestCancleApplyTaxiPot(1, token);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void 택시팟_신청_취소_ApplyNotFoundException_테스트() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user = dummyDataCreatService.makeUser(2345);
        String token = makeAccessToken(String.valueOf(user.getGcn()));
        // when
        ResultActions resultActions = requestCancleApplyTaxiPot(1, token);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestCancleApplyTaxiPot(int id, String token) throws Exception {
        return requestMvc(delete("/taxi-pot/sub/" + id).header("AUTHORIZATION", "Bearer " + token));
    }
}
