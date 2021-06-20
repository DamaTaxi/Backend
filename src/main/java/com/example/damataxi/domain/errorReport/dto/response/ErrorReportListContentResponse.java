package com.example.damataxi.domain.errorReport.dto.response;

import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ErrorReportListContentResponse {
    private int id;
    private String title;

    public static ErrorReportListContentResponse from(ErrorReport errorReport) {
        return ErrorReportListContentResponse.builder()
                .id(errorReport.getId())
                .title(errorReport.getTitle())
                .build();
    }
}
