package com.prince.finance.finsecure.DTO;

import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinancialRecordResponseDto {

     private Long fid;
     private BigDecimal amount;
     private Type type;
     private Category category;
     private LocalDateTime date;
     private String note;
     private Long userId;
     private String username;
}
