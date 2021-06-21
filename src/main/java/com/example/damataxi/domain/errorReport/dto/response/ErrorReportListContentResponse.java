package com.example.damataxi.domain.errorReport.dto.response;

import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ErrorReportListContentResponse {

    @ApiModelProperty(value = "오류 신고 아이디", example = "1")
    private int id;

    @ApiModelProperty(value = "오류 신고 제목", example = "오류 신고가 안됩니다.")
    private String title;

    public static ErrorReportListContentResponse from(ErrorReport errorReport) {
        return ErrorReportListContentResponse.builder()
                .id(errorReport.getId())
                .title(errorReport.getTitle())
                .build();
    }
}
