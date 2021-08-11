package com.example.damataxi.domain.suggestion.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SuggestionPage {

    @ApiModelProperty(value = "기능 건의 개수", example = "4")
    private final long totalElements;

    @ApiModelProperty(value = "기능 건의 리스트 총 페이지 수", example = "3")
    private final int totalPages;

    @ApiModelProperty(value = "기능 건의 리스트")
    private final List<SuggestionListContentResponse> content;

}
