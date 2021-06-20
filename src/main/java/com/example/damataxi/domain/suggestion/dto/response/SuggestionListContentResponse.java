package com.example.damataxi.domain.suggestion.dto.response;

import com.example.damataxi.domain.suggestion.domain.Suggestion;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SuggestionListContentResponse {
    private int id;
    private String title;

    public static SuggestionListContentResponse from(Suggestion suggestion) {
        return SuggestionListContentResponse.builder()
                .id(suggestion.getId())
                .title(suggestion.getTitle())
                .build();
    }
}
