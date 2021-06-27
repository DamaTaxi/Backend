package com.example.damataxi.domain.suggestion.service.impl;

import com.example.damataxi.domain.suggestion.domain.Suggestion;
import com.example.damataxi.domain.suggestion.domain.SuggestionRepository;
import com.example.damataxi.domain.suggestion.dto.request.SuggestionContentRequest;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionContentResponse;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionListContentResponse;
import com.example.damataxi.domain.suggestion.service.SuggestionService;
import com.example.damataxi.global.error.exception.SuggestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;

    @Override
    public List<SuggestionListContentResponse> getSuggestionList() {
        return suggestionRepository.findAll()
                .stream().map(SuggestionListContentResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public SuggestionContentResponse getSuggestionContent(int id) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(()-> new SuggestionNotFoundException(id));
        return SuggestionContentResponse.from(suggestion);
    }

    @Override
    public void makeSuggestion(SuggestionContentRequest request) {
        Suggestion suggestion = Suggestion.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        suggestionRepository.save(suggestion);
    }

    @Override
    public void deleteSuggestion(int id) {
        suggestionRepository.findById(id)
                .orElseThrow(()-> new SuggestionNotFoundException(id));
        suggestionRepository.deleteById(id);
    }
}
