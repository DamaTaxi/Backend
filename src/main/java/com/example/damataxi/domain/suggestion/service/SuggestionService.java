package com.example.damataxi.domain.suggestion.service;

import com.example.damataxi.domain.suggestion.dto.request.SuggestionContentRequest;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionContentResponse;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionListContentResponse;

import java.util.List;

public interface SuggestionService {
    List<SuggestionListContentResponse> getSuggestionList(int size, int page);
    SuggestionContentResponse getSuggestionContent(int id);
    void makeSuggestion(SuggestionContentRequest request);
    void deleteSuggestion(int id);
}
