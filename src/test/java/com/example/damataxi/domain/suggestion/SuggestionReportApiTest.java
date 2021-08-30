package com.example.damataxi.domain.suggestion;

import com.example.damataxi.ApiTest;
import com.example.damataxi.DummyDataCreatService;
import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.suggestion.domain.Suggestion;
import com.example.damataxi.domain.suggestion.domain.SuggestionRepository;
import com.example.damataxi.domain.suggestion.dto.request.SuggestionContentRequest;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SuggestionReportApiTest extends ApiTest {
    @Autowired
    private DummyDataCreatService dummyDataCreatService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SuggestionRepository suggestionRepository;

    @Test
    public void 기능_건의_리스트_받아오기_테스트() throws Exception {
        // given
        Admin admin = dummyDataCreatService.makeAdmin("admin");
        String token = makeAccessToken(admin.getUsername());
        dummyDataCreatService.makeSuggestion();
        dummyDataCreatService.makeSuggestion();

        // when
        ResultActions resultActions = requestGetSuggestionList(2, 0, token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        SuggestionPage response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<SuggestionPage>() {});

        Assertions.assertEquals(response.getTotalElements(), 2);
        Assertions.assertEquals(response.getTotalPages(), 1);
        Assertions.assertEquals(response.getContent().size(), 2);
        Assertions.assertEquals(response.getContent().get(0).getTitle(), "testTitle");
        Assertions.assertEquals(response.getContent().get(1).getTitle(), "testTitle");
    }

    private ResultActions requestGetSuggestionList(int size, int page, String token) throws Exception {
        return requestMvc(get("/suggestion?size=" + size + "&page=" + page).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 기능_건의_정보_받아오기_테스트() throws Exception {
        // given
        Admin admin = dummyDataCreatService.makeAdmin("admin");
        String token = makeAccessToken(admin.getUsername());
        Suggestion suggestion = dummyDataCreatService.makeSuggestion();

        // when
        ResultActions resultActions = requestGetSuggestionContent(suggestion.getId(), token);

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value("testTitle"))
                .andExpect(jsonPath("content").value("testContent"))
                .andDo(print());
    }

    private ResultActions requestGetSuggestionContent(int id, String token) throws Exception {
        return requestMvc(get("/suggestion/" + id).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 기능_건의_등록_테스트() throws Exception {
        // given
        SuggestionContentRequest request = new SuggestionContentRequest("문제있음", "사실 문제 없음");

        // when
        ResultActions resultActions = requestPostSuggestion(request);

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print());

        List<Suggestion> suggestions = suggestionRepository.findAll();

        Assertions.assertEquals(suggestions.size(), 1);
        Assertions.assertEquals(suggestions.get(0).getTitle(), "문제있음");
        Assertions.assertEquals(suggestions.get(0).getContent(), "사실 문제 없음");
    }

    private ResultActions requestPostSuggestion(SuggestionContentRequest request) throws Exception {
        return requestMvc(post("/suggestion"), request);
    }

    @Test
    public void 오류_신고_삭제_테스트() throws Exception {
        // given
        Admin admin = dummyDataCreatService.makeAdmin("admin");
        String token = makeAccessToken(admin.getUsername());
        Suggestion suggestion = dummyDataCreatService.makeSuggestion();

        // when
        ResultActions resultActions = requestDeleteSuggestion(suggestion.getId(), token);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        Assertions.assertTrue(suggestionRepository.findAll().isEmpty());
    }

    private ResultActions requestDeleteSuggestion(int id, String token) throws Exception {
        return requestMvc(delete("/suggestion/" + id).header("AUTHORIZATION", "Bearer " + token));
    }
}
