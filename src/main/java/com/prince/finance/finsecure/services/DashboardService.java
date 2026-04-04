package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.CategorySummaryDto;
import com.prince.finance.finsecure.DTO.DashboardSummaryDto;
import com.prince.finance.finsecure.DTO.MonthlyTrendDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;

import java.util.List;

public interface DashboardService {
    DashboardSummaryDto getSummary(Long userId);
    List<CategorySummaryDto> getCategorySummary(Long userId);
    List<MonthlyTrendDto> getMonthlyTrend(Long userId);
    List<FinancialRecordResponseDto> getRecentTransactions(Long userId);
}