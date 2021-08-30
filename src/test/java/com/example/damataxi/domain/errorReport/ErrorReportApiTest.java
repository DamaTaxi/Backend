package com.example.damataxi.domain.errorReport;

import com.example.damataxi.ApiTest;
import com.example.damataxi.DummyDataCreatService;
import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import com.example.damataxi.domain.errorReport.domain.ErrorReportRepository;
import com.example.damataxi.domain.errorReport.dto.request.ErrorReportContentRequest;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportPage;
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

public class ErrorReportApiTest extends ApiTest {

    @Autowired
    private DummyDataCreatService dummyDataCreatService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ErrorReportRepository errorReportRepository;

    @Test
    public void 오류_신고_리스트_받아오기_테스트() throws Exception {
        // given
        Admin admin = dummyDataCreatService.makeAdmin("admin");
        String token = makeAccessToken(admin.getUsername());
        dummyDataCreatService.makeErrorReport();
        dummyDataCreatService.makeErrorReport();

        // when
        ResultActions resultActions = requestGetErrorReportList(2, 0, token);

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ErrorReportPage response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<ErrorReportPage>() {});

        Assertions.assertEquals(response.getTotalElements(), 2);
        Assertions.assertEquals(response.getTotalPages(), 1);
        Assertions.assertEquals(response.getContent().size(), 2);
        Assertions.assertEquals(response.getContent().get(0).getTitle(), "testTitle");
        Assertions.assertEquals(response.getContent().get(1).getTitle(), "testTitle");
    }

    private ResultActions requestGetErrorReportList(int size, int page, String token) throws Exception {
        return requestMvc(get("/error-report?size=" + size + "&page=" + page).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 오류_신고_정보_받아오기_테스트() throws Exception {
        // given
        Admin admin = dummyDataCreatService.makeAdmin("admin");
        String token = makeAccessToken(admin.getUsername());
        ErrorReport errorReport = dummyDataCreatService.makeErrorReport();

        // when
        ResultActions resultActions = requestGetErrorReportContent(errorReport.getId(), token);

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value("testTitle"))
                .andExpect(jsonPath("content").value("testContent"))
                .andDo(print());
    }

    private ResultActions requestGetErrorReportContent(int id, String token) throws Exception {
        return requestMvc(get("/error-report/" + id).header("AUTHORIZATION", "Bearer " + token));
    }

    @Test
    public void 오류_신고_등록_테스트() throws Exception {
        // given
        ErrorReportContentRequest request = new ErrorReportContentRequest("문제있음", "사실 문제 없음");

        // when
        ResultActions resultActions = requestPostErrorReport(request);

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print());

        List<ErrorReport> errorReports = errorReportRepository.findAll();

        Assertions.assertEquals(errorReports.size(), 1);
        Assertions.assertEquals(errorReports.get(0).getTitle(), "문제있음");
        Assertions.assertEquals(errorReports.get(0).getContent(), "사실 문제 없음");
    }

    private ResultActions requestPostErrorReport(ErrorReportContentRequest request) throws Exception {
        return requestMvc(post("/error-report"), request);
    }

    @Test
    public void 오류_신고_삭제_테스트() throws Exception {
        // given
        Admin admin = dummyDataCreatService.makeAdmin("admin");
        String token = makeAccessToken(admin.getUsername());
        ErrorReport errorReport = dummyDataCreatService.makeErrorReport();

        // when
        ResultActions resultActions = requestDeleteErrorReport(errorReport.getId(), token);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        Assertions.assertTrue(errorReportRepository.findAll().isEmpty());
    }

    private ResultActions requestDeleteErrorReport(int id, String token) throws Exception {
        return requestMvc(delete("/error-report/" + id).header("AUTHORIZATION", "Bearer " + token));
    }
}
