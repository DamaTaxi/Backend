package com.example.damataxi.domain.suggestion.controller;

import com.example.damataxi.domain.suggestion.dto.request.SuggestionContentRequest;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionContentResponse;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionListContentResponse;
import com.example.damataxi.domain.suggestion.dto.response.SuggestionPage;
import com.example.damataxi.domain.suggestion.service.SuggestionService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "기능 건의 리스트 받아오기", notes = "모든 기능 건의 리스트를 받아옵니다 (어드민)")
    @GetMapping
    public SuggestionPage getSuggestionList(@RequestParam("size") int size, @RequestParam("page") int page) {
        return suggestionService.getSuggestionList(size, page);
    }

    @ApiOperation(value = "기능 건의 내용 받아오기", notes = "기능 건의 아이디를 받고 기능 건의 내용을 반환합니다 (어드민)")
    @GetMapping("/{id}")
    public SuggestionContentResponse getSuggestionContent(@PathVariable("id") int id) {
        return suggestionService.getSuggestionContent(id);
    }

    @ApiOperation(value = "기능 건의하기", notes = "기능 건의를 합니다 (ALL)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeSuggestion(@Valid @RequestBody SuggestionContentRequest request){
        suggestionService.makeSuggestion(request);
    }

    @ApiOperation(value = "기능 건의 삭제하기", notes = "기능 건의를 삭제합니다 (어드민)")
    @DeleteMapping("/{id}")
    public void deleteSuggestion(@PathVariable("id") int id){
        suggestionService.deleteSuggestion(id);
    }
}
