package com.example.damataxi.domain.errorReport.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorReportPage {

    @ApiModelProperty(value = "오류 신고 개수", example = "4")
    private final long totalElements;

    @ApiModelProperty(value = "오류 신고 리스트 총 페이지 수", example = "3")
    private final int totalPages;

    @ApiModelProperty(value = "오류 신고 리스트")
    private final List<ErrorReportListContentResponse> content;

}
