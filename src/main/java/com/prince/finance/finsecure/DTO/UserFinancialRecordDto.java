package com.prince.finance.finsecure.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserFinancialRecordDto {
    private Long userId;
    private String username;
    private String email;
    private List<FinancialRecordResponseDto> records;
}
