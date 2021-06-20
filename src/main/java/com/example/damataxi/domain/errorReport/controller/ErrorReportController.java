package com.example.damataxi.domain.errorReport.controller;

import com.example.damataxi.domain.errorReport.dto.request.ErrorReportContentRequest;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportContentResponse;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportListContentResponse;
import com.example.damataxi.domain.errorReport.service.ErrorReportService;
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

    @GetMapping
    public List<ErrorReportListContentResponse> getErrorReportList() {
        return errorReportService.getErrorReportList();
    }

    @GetMapping("/{id}")
    public ErrorReportContentResponse getErrorReportContent(@PathVariable("id") int id) {
        return errorReportService.getErrorReportContent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeErrorReport(@Valid @RequestBody ErrorReportContentRequest request){
        errorReportService.makeErrorReport(request);
    }

    @DeleteMapping("/{id}")
    public void deleteErrorReport(@PathVariable("id") int id){
        errorReportService.deleteErrorReport(id);
    }
}
