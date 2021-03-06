package com.example.damataxi.domain.errorReport.service;

import com.example.damataxi.domain.errorReport.dto.request.ErrorReportContentRequest;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportContentResponse;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportPage;

public interface ErrorReportService {
    ErrorReportPage getErrorReportList(int size, int page);
    ErrorReportContentResponse getErrorReportContent(int id);
    void makeErrorReport(ErrorReportContentRequest request);
    void deleteErrorReport(int id);
}
