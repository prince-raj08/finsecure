package com.prince.finance.finsecure.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardSummaryDto {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
}