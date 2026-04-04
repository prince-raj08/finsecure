package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.CategorySummaryDto;
import com.prince.finance.finsecure.DTO.DashboardSummaryDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.MonthlyTrendDto;
import com.prince.finance.finsecure.entities.Financial_record;
import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import com.prince.finance.finsecure.exceptions.CommonExceptions;
import com.prince.finance.finsecure.repository.FinancialRecordRepository;
import com.prince.finance.finsecure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final FinancialRecordRepository financialRecordRepository;
    private final UserRepository userRepository;


    @Override
    public DashboardSummaryDto getSummary(Long userId) {
        log.info("Fetching summary for user ID: {}", userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new CommonExceptions(
                        "User not found with id: " + userId));

        List<Financial_record> records =
                financialRecordRepository.findByUserIdAndDeletedFalse(userId);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Financial_record record : records) {
            if (record.getType() == Type.INCOME) {
                totalIncome = totalIncome.add(record.getAmount());
            } else if (record.getType() == Type.EXPENSE) {
                totalExpense = totalExpense.add(record.getAmount());
            }
        }

        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        DashboardSummaryDto summary = new DashboardSummaryDto();
        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        summary.setNetBalance(netBalance);

        log.info("Summary for user {}: Income={}, Expense={}, Balance={}",
                userId, totalIncome, totalExpense, netBalance);
        return summary;
    }

    @Override
    public List<CategorySummaryDto> getCategorySummary(Long userId) {
        log.info("Fetching category summary for user ID: {}", userId);

        List<Financial_record> records =
                financialRecordRepository.findByUserIdAndDeletedFalse(userId);

        List<CategorySummaryDto> result = new ArrayList<>();

        for (Category category : Category.values()) {
            BigDecimal total = BigDecimal.ZERO;

            for (Financial_record record : records) {
                if (record.getCategory() == category) {
                    total = total.add(record.getAmount());
                }
            }

            // Sirf wo category dikhao jisme kuch amount ho
            if (total.compareTo(BigDecimal.ZERO) > 0) {
                CategorySummaryDto dto = new CategorySummaryDto();
                dto.setCategory(category);
                dto.setTotalAmount(total);
                result.add(dto);
            }
        }

        return result;
    }


    @Override
    public List<MonthlyTrendDto> getMonthlyTrend(Long userId) {
        log.info("Fetching monthly trend for user ID: {}", userId);

        List<Financial_record> records =
                financialRecordRepository.findByUserIdAndDeletedFalse(userId);

        // Har month ka data
        List<MonthlyTrendDto> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            BigDecimal monthlyIncome = BigDecimal.ZERO;
            BigDecimal monthlyExpense = BigDecimal.ZERO;
            boolean hasData = false;

            for (Financial_record record : records) {
                if (record.getDate().getMonthValue() == month) {
                    hasData = true;
                    if (record.getType() == Type.INCOME) {
                        monthlyIncome = monthlyIncome.add(record.getAmount());
                    } else if (record.getType() == Type.EXPENSE) {
                        monthlyExpense = monthlyExpense.add(record.getAmount());
                    }
                }
            }

            if (hasData) {
                MonthlyTrendDto dto = new MonthlyTrendDto();
                dto.setMonth(month);
                dto.setYear(records.get(0).getDate().getYear());
                dto.setTotalIncome(monthlyIncome);
                dto.setTotalExpense(monthlyExpense);
                dto.setNetBalance(monthlyIncome.subtract(monthlyExpense));
                result.add(dto);
            }
        }

        return result;
    }

    @Override
    public List<FinancialRecordResponseDto> getRecentTransactions(Long userId) {
        log.info("Fetching recent transactions for user ID: {}", userId);

        List<Financial_record> records =
                financialRecordRepository.findByUserIdAndDeletedFalse(userId);

        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {
                if (records.get(i).getDate()
                        .isBefore(records.get(j).getDate())) {
                    Financial_record temp = records.get(i);
                    records.set(i, records.get(j));
                    records.set(j, temp);
                }
            }
        }

        List<FinancialRecordResponseDto> result = new ArrayList<>();
        int limit = Math.min(5, records.size());

        for (int i = 0; i < limit; i++) {
            Financial_record record = records.get(i);
            FinancialRecordResponseDto dto = new FinancialRecordResponseDto();
            dto.setFid(record.getFid());
            dto.setAmount(record.getAmount());
            dto.setType(record.getType());
            dto.setCategory(record.getCategory());
            dto.setDate(record.getDate());
            dto.setNote(record.getNote());
            dto.setUserId(record.getUser().getId());
            dto.setUsername(record.getUser().getUsername());
            result.add(dto);
        }

        return result;
    }
}