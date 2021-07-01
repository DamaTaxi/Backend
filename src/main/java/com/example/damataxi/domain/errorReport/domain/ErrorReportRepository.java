package com.example.damataxi.domain.errorReport.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorReportRepository extends JpaRepository<ErrorReport, Integer> {
    Page<ErrorReport> findAll(Pageable pageable);
}
