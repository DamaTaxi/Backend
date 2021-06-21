package com.example.damataxi.domain.errorReport.dto.response;

import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ErrorReportContentResponse {

    @ApiModelProperty(value = "오류 신고 제목", example = "오류 신고가 안됩니다.")
    private String title;

    @ApiModelProperty(value = "오류 신고 내용", example = "오류 신고가 되네요...하하")
    private String content;

    public static ErrorReportContentResponse from(ErrorReport errorReport) {
        return ErrorReportContentResponse.builder()
                .title(errorReport.getTitle())
                .content(errorReport.getContent())
                .build();
    }
}
