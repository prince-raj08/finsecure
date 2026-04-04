package com.prince.finance.finsecure.controller;

import com.prince.finance.finsecure.DTO.CategorySummaryDto;
import com.prince.finance.finsecure.DTO.DashboardSummaryDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.MonthlyTrendDto;
import com.prince.finance.finsecure.services.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @GetMapping("/{userId}/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary(
            @PathVariable Long userId) {
        log.info("Fetching summary for user ID: {}", userId);
        DashboardSummaryDto response = dashboardService.getSummary(userId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @GetMapping("/{userId}/category")
    public ResponseEntity<List<CategorySummaryDto>> getCategorySummary(
            @PathVariable Long userId) {
        log.info("Fetching category summary for user ID: {}", userId);
        List<CategorySummaryDto> response =
                dashboardService.getCategorySummary(userId);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @GetMapping("/{userId}/monthly")
    public ResponseEntity<List<MonthlyTrendDto>> getMonthlyTrend(
            @PathVariable Long userId) {
        log.info("Fetching monthly trend for user ID: {}", userId);
        List<MonthlyTrendDto> response =
                dashboardService.getMonthlyTrend(userId);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/recent")
    public ResponseEntity<List<FinancialRecordResponseDto>> getRecentTransactions(
            @PathVariable Long userId) {
        log.info("Fetching recent transactions for user ID: {}", userId);
        List<FinancialRecordResponseDto> response =
                dashboardService.getRecentTransactions(userId);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }
}