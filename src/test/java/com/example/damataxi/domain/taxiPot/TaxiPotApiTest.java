package com.example.damataxi.domain.taxiPot;

import com.example.damataxi.ApiTest;
import com.example.damataxi.DummyDataCreatService;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.dto.TaxiPotContentTestRequest;
import com.example.damataxi.domain.taxiPot.dto.TaxiPotContentTestResponse;
import com.example.damataxi.domain.taxiPot.dto.TaxiPotPageTestResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotSlideContentResponse;
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
    public void ?????????_??????_?????????() throws Exception {
        // given
        User user1 = dummyDataCreatService.makeUser("1234","aaaa@gmail.com");
        User user2 = dummyDataCreatService.makeUser("2345","bbbb@gmail.com");
        User user3 = dummyDataCreatService.makeUser("3456","cccc@gmail.com");
        User user4 = dummyDataCreatService.makeUser("1567","dddd@gmail.com");
        User user5 = dummyDataCreatService.makeUser("2678","eeee@gmail.com");
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
    public void ?????????_????????????_????????????_??????_??????_?????????() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        TaxiPot taxiPot1 =dummyDataCreatService.makeTaxiPot(creator1);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator2 = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        TaxiPot taxiPot2 =dummyDataCreatService.makeTaxiPot(creator2);
        dummyDataCreatService.makeReservation(taxiPot2, creator2);

        User creator3 = dummyDataCreatService.makeUser("3456", "cccc@gmail.com");
        TaxiPot taxiPot3 =dummyDataCreatService.makeTaxiPot(creator3);
        dummyDataCreatService.makeReservation(taxiPot3, creator3);

        User creator4 = dummyDataCreatService.makeUser("3210", "dddd@gmail.com");
        TaxiPot taxiPot4 =dummyDataCreatService.makeTaxiPot(creator4);
        dummyDataCreatService.makeReservation(taxiPot4, creator4);

        // when
        ResultActions resultActions = requestGetTaxiPotSlide();

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<TaxiPotSlideContentResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<TaxiPotSlideContentResponse>>() {});

        Assertions.assertEquals(response.size(), 4);
    }

    private ResultActions requestGetTaxiPotSlide() throws Exception {
        return requestMvc(get("/taxi-pot/slide"));
    }

    @Test
    public void ?????????_????????????_????????????_????????????_target??????_?????????() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        TaxiPot taxiPot1 =dummyDataCreatService.makeTaxiPot(creator1);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator3 = dummyDataCreatService.makeUser("2222", "bbbb@gmail.com");
        TaxiPot taxiPot3 = dummyDataCreatService.makeTaxiPot(creator3, TaxiPotTarget.SOPHOMORE);
        dummyDataCreatService.makeReservation(taxiPot3, creator3);

        User creator4 = dummyDataCreatService.makeUser("1111", "cccc@gmail.com");
        TaxiPot taxiPot4 = dummyDataCreatService.makeTaxiPot(creator4, TaxiPotTarget.FRESHMAN);
        dummyDataCreatService.makeReservation(taxiPot4, creator4);

        User user = dummyDataCreatService.makeUser("1235", "dddd@gmail.com");
        String token = makeAccessToken(user.getEmail());
        TaxiPot taxiPot5 = dummyDataCreatService.makeTaxiPot(user, TaxiPotTarget.ALL);
        dummyDataCreatService.makeReservation(taxiPot5, user);

        // when
        ResultActions resultActions = requestGetTaxiPotSlide(token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<TaxiPotSlideContentResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<TaxiPotSlideContentResponse>>() {});

        Assertions.assertEquals(response.size(), 2);
    }

    private ResultActions requestGetTaxiPotSlide(String token) throws Exception {
        return requestMvc(get("/taxi-pot/slide").header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void ?????????_?????????_????????????_??????_??????_?????????() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        TaxiPot taxiPot1 =dummyDataCreatService.makeTaxiPot(creator1);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator2 = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        TaxiPot taxiPot2 =dummyDataCreatService.makeTaxiPot(creator2);
        dummyDataCreatService.makeReservation(taxiPot2, creator2);

        User creator3 = dummyDataCreatService.makeUser("3456", "cccc@gmail.com");
        TaxiPot taxiPot3 =dummyDataCreatService.makeTaxiPot(creator3);
        dummyDataCreatService.makeReservation(taxiPot3, creator3);

        User creator4 = dummyDataCreatService.makeUser("3210", "dddd@gmail.com");
        TaxiPot taxiPot4 =dummyDataCreatService.makeTaxiPot(creator4);
        dummyDataCreatService.makeReservation(taxiPot4, creator4);

        // when
        ResultActions resultActions = requestGetTaxiPotList(3, 0);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        TaxiPotPageTestResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<TaxiPotPageTestResponse>() {});

        Assertions.assertEquals(response.getContent().size(), 3);
        Assertions.assertEquals(response.getTotalElements(), 4);
        Assertions.assertEquals(response.getTotalPages(), 2);
    }

    private ResultActions requestGetTaxiPotList(int size, int page) throws Exception {
        return requestMvc(get("/taxi-pot?size=" + size + "&page=" + page));
    }

    @Test
    public void ?????????_?????????_????????????_????????????_target??????_?????????() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        TaxiPot taxiPot1 =dummyDataCreatService.makeTaxiPot(creator1);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator3 = dummyDataCreatService.makeUser("2222", "bbbb@gmail.com");
        TaxiPot taxiPot3 = dummyDataCreatService.makeTaxiPot(creator3, TaxiPotTarget.SOPHOMORE);
        dummyDataCreatService.makeReservation(taxiPot3, creator3);

        User creator4 = dummyDataCreatService.makeUser("1111", "cccc@gmail.com");
        TaxiPot taxiPot4 = dummyDataCreatService.makeTaxiPot(creator4, TaxiPotTarget.FRESHMAN);
        dummyDataCreatService.makeReservation(taxiPot4, creator4);

        User user = dummyDataCreatService.makeUser("1234", "dddd@gmail.com");
        String token = makeAccessToken(user.getEmail());
        // when
        ResultActions resultActions = requestGetTaxiPotList(3, 0, token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        TaxiPotPageTestResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<TaxiPotPageTestResponse>() {});

        Assertions.assertEquals(response.getContent().size(), 2);
        Assertions.assertEquals(response.getTotalElements(), 2);
        Assertions.assertEquals(response.getTotalPages(), 1);
    }

    @Test
    public void ?????????_?????????_????????????_????????????_????????????_?????????() throws Exception {
        // given
        User creator1 = dummyDataCreatService.makeUser("1111", "aaaa@gmail.com");
        TaxiPot taxiPot1 = dummyDataCreatService.makeTaxiPot(creator1, 1.0000, 1.0000);
        dummyDataCreatService.makeReservation(taxiPot1, creator1);

        User creator2 = dummyDataCreatService.makeUser("2222", "bbbb@gmail.com");
        TaxiPot taxiPot2 = dummyDataCreatService.makeTaxiPot(creator2, 2.0000, 2.0000);
        dummyDataCreatService.makeReservation(taxiPot2, creator2);

        User user = dummyDataCreatService.makeUser("3333", "cccc@gmail.com");
        user.setLatitude(3.0000);
        user.setLongitude(3.0000);
        userRepository.save(user);
        String token = makeAccessToken(user.getEmail());

        // when
        ResultActions resultActions = requestGetTaxiPotList(3, 0, token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        TaxiPotPageTestResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<TaxiPotPageTestResponse>() {});

        Assertions.assertEquals(response.getContent().size(), 2);
        Assertions.assertEquals(response.getContent().get(0).getLatitude(), 2.0000);
        Assertions.assertEquals(response.getContent().get(0).getLongitude(), 2.0000);
        Assertions.assertEquals(response.getContent().get(1).getLatitude(), 1.0000);
        Assertions.assertEquals(response.getContent().get(1).getLongitude(), 1.0000);
    }

    private ResultActions requestGetTaxiPotList(int size, int page, String token) throws Exception {
        return requestMvc(get("/taxi-pot?size=" + size + "&page=" + page).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void ?????????_??????_????????????_?????????() throws Exception {
        // given
        setupUtf8();
        User user = dummyDataCreatService.makeUser("1234");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        // when
        ResultActions resultActions = requestGetTaxiPotContent(taxiPot.getId());

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        TaxiPotContentTestResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<TaxiPotContentTestResponse>() {});

        Assertions.assertEquals(taxiPot.getTitle(), response.getTitle());
        Assertions.assertEquals(taxiPot.getAddress(), response.getAddress());

        Assertions.assertEquals(user.getGcn(), response.getCreator().getGcn());
        Assertions.assertEquals(user.getUsername(), response.getCreator().getName());
        Assertions.assertEquals(user.getTel(), response.getCreator().getNumber());

        Assertions.assertEquals(taxiPot.getReservations().size(), response.getReserve());
        Assertions.assertEquals(taxiPot.getAmount(), response.getAll());
        Assertions.assertEquals(taxiPot.getDestinationLatitude(), response.getLatitude());
        Assertions.assertEquals(taxiPot.getDestinationLongitude(), response.getLongitude());
        Assertions.assertEquals(taxiPot.getTitle(), response.getTitle());
        Assertions.assertEquals(taxiPot.getPlace(), response.getPlace());
        Assertions.assertEquals(taxiPot.getContent(), response.getContent());

        Assertions.assertEquals(user.getGcn(), response.getUsers().get(0).getGcn());
        Assertions.assertEquals(user.getUsername(), response.getUsers().get(0).getName());
        Assertions.assertEquals(user.getTel(), response.getUsers().get(0).getNumber());

    }

    @Test
    public void ?????????_??????_????????????_TaxiPotNotFoundException_?????????() throws Exception {
        // given
        dummyDataCreatService.makeUser("1234");

        // when
        ResultActions resultActions = requestGetTaxiPotContent(1);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestGetTaxiPotContent(int id) throws Exception {
        return requestMvc(get("/taxi-pot/" + id));
    }

    @Test
    public void ?????????_?????????_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234");
        String token = makeAccessToken(user.getEmail());
        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestMakeTaxiPot(request, token);

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
        Reservation reservation = reservationRepository.findByIdUserEmail(user.getEmail())
                .orElseThrow(()-> new UserNotFoundException(user.getEmail()));
        TaxiPot taxiPot = reservation.getTaxiPot();

        Assertions.assertEquals(taxiPot.getCreator().getGcn(), "1234");
        Assertions.assertEquals(taxiPot.getTarget(), TaxiPotTarget.ALL);
        Assertions.assertEquals(taxiPot.getPlace(), request.getPlace());
        Assertions.assertEquals(taxiPot.getDestinationLatitude(), request.getLatitude());
        Assertions.assertEquals(taxiPot.getDestinationLongitude(), request.getLongitude());
        Assertions.assertEquals(taxiPot.getTitle(), request.getTitle());
        Assertions.assertEquals(taxiPot.getAmount(), request.getAmount());
        Assertions.assertNull(taxiPot.getContent());
    }

    @Test
    public void ?????????_?????????_AlreadyApplyException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        String token = makeAccessToken(user.getEmail());

        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestMakeTaxiPot(request, token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_?????????_InvalidInputValueException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234");
        String token = makeAccessToken(user.getEmail());
        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("SENIOR");

        // when
        ResultActions resultActions = requestMakeTaxiPot(request, token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private TaxiPotContentTestRequest makeTaxiPotContentRequest(String target) {
        return TaxiPotContentTestRequest.builder()
                .target(target)
                .meetingAt("2020-03-01-12:00")
                .place("????????? 322???")
                .latitude(12.3456)
                .longitude(23.4567)
                .amount(3)
                .title("??????????????????????????????")
                .address("????????????????????????")
                .build();
    }

    private ResultActions requestMakeTaxiPot(TaxiPotContentTestRequest request, String token) throws Exception {
        return requestMvc(post("/taxi-pot").header("AUTHORIZATION", "Bearer " + token), request);
    }

    @Test
    public void ?????????_??????_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234");
        String token = makeAccessToken(user.getEmail());
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        TaxiPot patchedTaxiPot = taxiPotRepository.findById(taxiPot.getId())
                .orElseThrow(()-> { throw new TaxiPotNotFoundException(taxiPot.getId()); });

        Assertions.assertEquals(patchedTaxiPot.getCreator().getGcn(), "1234");
        Assertions.assertEquals(patchedTaxiPot.getTarget(), TaxiPotTarget.ALL);
        Assertions.assertEquals(patchedTaxiPot.getPlace(), request.getPlace());
        Assertions.assertEquals(patchedTaxiPot.getDestinationLatitude(), request.getLatitude());
        Assertions.assertEquals(patchedTaxiPot.getDestinationLongitude(), request.getLongitude());
        Assertions.assertEquals(patchedTaxiPot.getTitle(), request.getTitle());
        Assertions.assertEquals(patchedTaxiPot.getAmount(), request.getAmount());
        Assertions.assertNull(patchedTaxiPot.getContent());
    }

    @Test
    public void ?????????_??????_NotCreatorException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        String token = makeAccessToken(user.getEmail());

        User fakeUser = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(fakeUser);
        dummyDataCreatService.makeReservation(taxiPot, fakeUser);
        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_ImpossibleChangeException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234", "aaaa@gamil.com");
        String token = makeAccessToken(user.getEmail());
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        User reservedUser = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        dummyDataCreatService.makeReservation(taxiPot, reservedUser);
        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("ALL");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_InvalidInputValueException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234");
        String token = makeAccessToken(user.getEmail());
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        TaxiPotContentTestRequest request = makeTaxiPotContentRequest("SENIOR");

        // when
        ResultActions resultActions = requestPatchTaxiPot(request, taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestPatchTaxiPot(TaxiPotContentTestRequest request, int id, String token) throws Exception {
        return requestMvc(patch("/taxi-pot/" + id).header("AUTHORIZATION", "Bearer " + token), request);
    }

    @Test
    public void ?????????_??????_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234");
        String token = makeAccessToken(user.getEmail());
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        Reservation reservation = dummyDataCreatService.makeReservation(taxiPot, user);

        // when
        ResultActions resultActions = requestDeleteTaxiPot(taxiPot.getId(), token);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        User changedUser = userRepository.findById(user.getEmail())
                .orElseThrow(()-> { throw new UserNotFoundException(user.getUsername()); });

        Assertions.assertNull(changedUser.getReservation());
        Assertions.assertEquals(taxiPotRepository.findById(taxiPot.getId()), Optional.empty());
        Assertions.assertEquals(reservationRepository.findById(reservation.getId()), Optional.empty());
    }

    @Test
    public void ?????????_??????_NotCreatorException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234", "aaaa@gamil.com");
        String token = makeAccessToken(user.getEmail());
        User fakeUser = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(fakeUser);
        dummyDataCreatService.makeReservation(taxiPot, fakeUser);

        // when
        ResultActions resultActions = requestDeleteTaxiPot(taxiPot.getId(), token);

        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_ImpossibleChangeException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234", "aaaa@gamil.com");
        String token = makeAccessToken(user.getEmail());
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);
        User reservedUser = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
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
    public void ?????????_??????_?????????() throws Exception {
        User user = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        String token = makeAccessToken(user.getEmail());
        User creator = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);
        // when
        ResultActions resultActions = requestApplyTaxiPot(taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
        User reservedUser = userRepository.findById(user.getEmail())
                .orElseThrow(()-> { throw new UserNotFoundException(user.getUsername()); });
        Assertions.assertNotNull(reservedUser.getReservation());
        Assertions.assertTrue(reservationRepository.findByIdUserEmail(user.getEmail()).isPresent());
    }

    @Test
    public void ?????????_??????_AlreadyApplyException_?????????() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser("2345");
        String token = makeAccessToken(creator.getEmail());
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);
        // when
        ResultActions resultActions = requestApplyTaxiPot(taxiPot.getId(), token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_TaxiPotFinishedReservationException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        String token = makeAccessToken(user.getEmail());

        User creator = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user1 = dummyDataCreatService.makeUser("1111", "cccc@gmail.com");
        User user2 = dummyDataCreatService.makeUser("2222", "dddd@gmail.com");
        User user3 = dummyDataCreatService.makeUser("3333", "eeee@gmail.com");
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
    public void ?????????_??????_TaxiPotNotFoundException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("2345");
        String token = makeAccessToken(user.getEmail());
        // when
        ResultActions resultActions = requestApplyTaxiPot(1, token);
        // then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_InvalidInputValueException_?????????() throws Exception {
        // given
        User user = dummyDataCreatService.makeUser("3456", "aaaa@gmail.com");
        String token = makeAccessToken(user.getEmail());

        User creator = dummyDataCreatService.makeUser("1234", "bbbb@gmail.com");
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
    public void ?????????_??????_??????_?????????() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser("1234", "aaaa@gmail.com");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        dummyDataCreatService.makeReservation(taxiPot, user);
        String token = makeAccessToken(user.getEmail());
        // when
        ResultActions resultActions = requestCancleApplyTaxiPot(taxiPot.getId(), token);
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        User refreshUser = userRepository.findById(user.getEmail())
                .orElseThrow(()-> { throw new UserNotFoundException(user.getUsername()); });

        Assertions.assertNull(refreshUser.getReservation());
        Assertions.assertFalse(reservationRepository.findByIdUserEmail(user.getEmail()).isPresent());
    }

    @Test
    public void ?????????_??????_??????_TaxiPotNotFoundException_?????????() throws Exception {
        // given;
        User user = dummyDataCreatService.makeUser("2345", "bbbb@gmail.com");
        String token = makeAccessToken(user.getEmail());
        // when
        ResultActions resultActions = requestCancleApplyTaxiPot(1, token);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void ?????????_??????_??????_ApplyNotFoundException_?????????() throws Exception {
        // given
        User creator = dummyDataCreatService.makeUser("1234", "aaaa@gamil.com");
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(creator);
        dummyDataCreatService.makeReservation(taxiPot, creator);

        User user = dummyDataCreatService.makeUser("2345", "bbbb@gamil.com");
        String token = makeAccessToken(user.getEmail());
        // when
        ResultActions resultActions = requestCancleApplyTaxiPot(taxiPot.getId(), token);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestCancleApplyTaxiPot(int id, String token) throws Exception {
        return requestMvc(delete("/taxi-pot/sub/" + id).header("AUTHORIZATION", "Bearer " + token));
    }
}
