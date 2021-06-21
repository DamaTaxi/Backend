package com.example.damataxi.domain.errorReport.controller;

import com.example.damataxi.domain.errorReport.dto.request.ErrorReportContentRequest;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportContentResponse;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportListContentResponse;
import com.example.damataxi.domain.errorReport.service.ErrorReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/error-report")
public class ErrorReportController {

    private final ErrorReportService errorReportService;

    @ApiOperation(value = "오류 신고 리스트 받아오기", notes = "모든 오류 신고 리스트를 받아옵니다 (어드민)")
    @GetMapping
    public List<ErrorReportListContentResponse> getErrorReportList() {
        return errorReportService.getErrorReportList();
    }

    @ApiOperation(value = "오류 신고 내용 받아오기", notes = "오류 신고 아이디를 받고 오류 신고 내용을 반환합니다 (어드민)")
    @GetMapping("/{id}")
    public ErrorReportContentResponse getErrorReportContent(@PathVariable("id") int id) {
        return errorReportService.getErrorReportContent(id);
    }

    @ApiOperation(value = "오류 신고하기", notes = "오류 신고를 합니다 (ALL)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeErrorReport(@Valid @RequestBody ErrorReportContentRequest request){
        errorReportService.makeErrorReport(request);
    }

    @ApiOperation(value = "오류 신고 삭제하기", notes = "오류 신고를 삭제합니다 (어드민)")
    @DeleteMapping("/{id}")
    public void deleteErrorReport(@PathVariable("id") int id){
        errorReportService.deleteErrorReport(id);
    }
}
