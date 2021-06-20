package com.example.damataxi.domain.suggestion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionContentRequest {
    @NotNull
    private String title;

    @NotNull
    private String content;
}
