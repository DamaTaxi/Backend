package com.example.damataxi.domain.suggestion.dto.response;

import com.example.damataxi.domain.suggestion.domain.Suggestion;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SuggestionContentResponse {
    private String title;
    private String content;

    public static SuggestionContentResponse from(Suggestion suggestion) {
        return SuggestionContentResponse.builder()
                .title(suggestion.getTitle())
                .content(suggestion.getContent())
                .build();
    }
}
