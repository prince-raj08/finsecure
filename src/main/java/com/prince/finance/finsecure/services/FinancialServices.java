package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.FinancialRecordDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.UserFinancialRecordDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FinancialServices {

    FinancialRecordResponseDto createRecord(FinancialRecordDto financialRecordDto);
    FinancialRecordResponseDto findRecordById(Long id);
    List<UserFinancialRecordDto> getAllRecords();
}
