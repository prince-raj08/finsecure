package com.prince.finance.finsecure.controller;


import com.prince.finance.finsecure.DTO.FinancialRecordDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.UserFinancialRecordDto;
import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import com.prince.finance.finsecure.services.FinancialServicesImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/financial_record")
public class FinancialRecordController {

    private final FinancialServicesImpl financialServices;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/update")
    public ResponseEntity<String> updateRecord(@PathVariable Long id,@RequestBody FinancialRecordDto financialRecordDto)
    {
        log.info("Update Request with id : {}",id);
        String response = financialServices.updateRecord(id,financialRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response
        );
    }




    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        log.info("Soft delete request with ID: {}", id);
        String response = financialServices.deleteRecord(id);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FinancialRecordResponseDto>> getRecordsByUser(
            @PathVariable Long userId) {
        log.info("Fetching records for user ID: {}", userId);
        List<FinancialRecordResponseDto> response =
                financialServices.getRecordsByUserId(userId);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/filter/type")
    public ResponseEntity<List<FinancialRecordResponseDto>> getByType(
            @RequestParam Type type) {
        log.info("Fetching records by type: {}", type);
        return ResponseEntity.ok(financialServices.getRecordsByType(type));
    }


    @GetMapping("/filter/category")
    public ResponseEntity<List<FinancialRecordResponseDto>> getByCategory(
            @RequestParam Category category) {
        log.info("Fetching records by category: {}", category);
        return ResponseEntity.ok(
                financialServices.getRecordsByCategory(category));
    }


    @GetMapping("/filter/date")
    public ResponseEntity<List<FinancialRecordResponseDto>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {
        log.info("Fetching records from {} to {}", start, end);
        return ResponseEntity.ok(
                financialServices.getRecordsByDateRange(start, end));
    }
}
