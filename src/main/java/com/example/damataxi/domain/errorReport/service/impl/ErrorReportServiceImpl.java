package com.example.damataxi.domain.errorReport.service.impl;

import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import com.example.damataxi.domain.errorReport.domain.ErrorReportRepository;
import com.example.damataxi.domain.errorReport.dto.request.ErrorReportContentRequest;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportContentResponse;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportListContentResponse;
import com.example.damataxi.domain.errorReport.dto.response.ErrorReportPage;
import com.example.damataxi.domain.errorReport.service.ErrorReportService;
import com.example.damataxi.global.error.exception.ErrorReportNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorReportServiceImpl implements ErrorReportService {

    private final ErrorReportRepository errorReportRepository;

    @Override
    public ErrorReportPage getErrorReportList(int size, int page) {
        Page<ErrorReport> errorReports = errorReportRepository.findAll(PageRequest.of(page, size));
        List<ErrorReportListContentResponse> content = errorReports
                .stream().map(ErrorReportListContentResponse::from)
                .collect(Collectors.toList());
        return new ErrorReportPage(errorReports.getTotalElements(), errorReports.getTotalPages(), content);
    }

    @Override
    public ErrorReportContentResponse getErrorReportContent(int id) {
        ErrorReport errorReport = errorReportRepository.findById(id)
                .orElseThrow(()-> new ErrorReportNotFoundException(id));
        return ErrorReportContentResponse.from(errorReport);
    }

    @Override
    public void makeErrorReport(ErrorReportContentRequest request) {
        ErrorReport errorReport = ErrorReport.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        errorReportRepository.save(errorReport);
    }

    @Override
    public void deleteErrorReport(int id) {
        errorReportRepository.findById(id)
                .orElseThrow(()-> new ErrorReportNotFoundException(id));
        errorReportRepository.deleteById(id);
    }
}
