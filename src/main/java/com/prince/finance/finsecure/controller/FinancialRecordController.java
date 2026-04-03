package com.prince.finance.finsecure.controller;


import com.prince.finance.finsecure.DTO.FinancialRecordDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.UserFinancialRecordDto;
import com.prince.finance.finsecure.services.FinaicialServicesImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/financial_record")
public class FinancialRecordController {

    private final FinaicialServicesImpl financialServices;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<FinancialRecordResponseDto> createRecord(@Valid @RequestBody FinancialRecordDto financialRecordDto)
    {
        log.info("Creating new financial record with ID : {}",financialRecordDto.getUserId());
        FinancialRecordResponseDto response = financialServices.createRecord(financialRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<FinancialRecordResponseDto> getRecordById(@PathVariable Long id)
    {
        log.info("Fetching record request with ID: {}", id);
        FinancialRecordResponseDto response = financialServices.findRecordById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserFinancialRecordDto>> getAllRecords() {
        log.info("Fetching all records request ");
        List<UserFinancialRecordDto> response =
                financialServices.getAllRecords();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }


}
