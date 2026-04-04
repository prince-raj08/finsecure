package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.FinancialRecordDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.UserFinancialRecordDto;
import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialServices {

    FinancialRecordResponseDto createRecord(FinancialRecordDto financialRecordDto);
    FinancialRecordResponseDto findRecordById(Long id);
    List<UserFinancialRecordDto> getAllRecords();
    String updateRecord(Long id,FinancialRecordDto financialRecordDto);
    List<FinancialRecordResponseDto> getRecordsByUserId(Long userId);
    String deleteRecord(Long id);
    List<FinancialRecordResponseDto> getRecordsByType(Type type);
    List<FinancialRecordResponseDto> getRecordsByCategory(Category category);
    List<FinancialRecordResponseDto> getRecordsByDateRange(LocalDateTime start, LocalDateTime end);
}
