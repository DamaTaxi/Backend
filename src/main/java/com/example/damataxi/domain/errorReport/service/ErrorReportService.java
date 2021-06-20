package com.example.damataxi.domain.errorReport.service;

import com.example.damataxi.domain.errorReport.dto.request.ErrorReportContentRequest;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportContentResponse;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportListContentResponse;

import java.util.List;

public interface ErrorReportService {
    List<ErrorReportListContentResponse> getErrorReportList();
    ErrorReportContentResponse getErrorReportContent(int id);
    void makeErrorReport(ErrorReportContentRequest request);
    void deleteErrorReport(int id);
}
