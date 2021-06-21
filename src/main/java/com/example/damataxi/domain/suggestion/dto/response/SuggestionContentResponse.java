package com.example.damataxi.domain.suggestion.dto.response;

import com.example.damataxi.domain.suggestion.domain.Suggestion;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SuggestionContentResponse {

    @ApiModelProperty(value = "기능 건의 제목", example = "기능 건의 기능을 넣어주세요")
    private String title;

    @ApiModelProperty(value = "기능 건의 내용", example = "기능 건의 기능이 있으면 좋을 것 같습니다")
    private String content;

    public static SuggestionContentResponse from(Suggestion suggestion) {
        return SuggestionContentResponse.builder()
                .title(suggestion.getTitle())
                .content(suggestion.getContent())
                .build();
    }
}
