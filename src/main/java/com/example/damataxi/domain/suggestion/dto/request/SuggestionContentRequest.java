package com.example.damataxi.domain.suggestion.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionContentRequest {

    @ApiModelProperty(value = "기능 건의 제목", example = "기능 건의 기능을 넣어주세요")
    @NotNull
    private String title;

    @ApiModelProperty(value = "기능 건의 내용", example = "기능 건의 기능이 있으면 좋을 것 같습니다")
    @NotNull
    private String content;
}
