package com.example.damataxi.domain.suggestion.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    Page<Suggestion> findAll(Pageable pageable);
}
