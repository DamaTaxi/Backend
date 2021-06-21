package com.example.damataxi.domain.suggestion.dto.response;

import com.example.damataxi.domain.suggestion.domain.Suggestion;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SuggestionListContentResponse {

    @ApiModelProperty(value = "기능 건의 아이디", example = "1")
    private int id;

    @ApiModelProperty(value = "기능 건의 제목", example = "기능 건의 기능을 넣어주세요")
    private String title;

    public static SuggestionListContentResponse from(Suggestion suggestion) {
        return SuggestionListContentResponse.builder()
                .id(suggestion.getId())
                .title(suggestion.getTitle())
                .build();
    }
}
