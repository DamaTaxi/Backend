package com.example.damataxi.domain.suggestion.controller;

import com.example.damataxi.domain.suggestion.dto.request.SuggestionContentRequest;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionContentResponse;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionListContentResponse;
import com.example.damataxi.domain.suggestion.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping
    public List<SuggestionListContentResponse> getSuggestionList() {
        return suggestionService.getSuggestionList();
    }

    @GetMapping("/{id}")
    public SuggestionContentResponse getSuggestionContent(@PathVariable("id") int id) {
        return suggestionService.getSuggestionContent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeSuggestion(@Valid @RequestBody SuggestionContentRequest request){
        suggestionService.makeSuggestion(request);
    }

    @DeleteMapping("/{id}")
    public void deleteSuggestion(@PathVariable("id") int id){
        suggestionService.deleteSuggestion(id);
    }
}
