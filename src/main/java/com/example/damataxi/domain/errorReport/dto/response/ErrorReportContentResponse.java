package com.example.damataxi.domain.errorReport.dto.response;

import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ErrorReportContentResponse {
    private String title;
    private String content;

    public static ErrorReportContentResponse from(ErrorReport errorReport) {
        return ErrorReportContentResponse.builder()
                .title(errorReport.getTitle())
                .content(errorReport.getContent())
                .build();
    }
}
