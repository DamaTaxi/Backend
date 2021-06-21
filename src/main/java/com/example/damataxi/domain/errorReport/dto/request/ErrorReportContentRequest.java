package com.example.damataxi.domain.errorReport.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReportContentRequest {
    @ApiModelProperty(value = "오류 신고 제목", example = "오류 신고가 안됩니다")
    @NotNull
    private String title;

    @ApiModelProperty(value = "오류 신고 내용", example = "오류 신고가 되네요...하하")
    @NotNull
    private String content;
}
