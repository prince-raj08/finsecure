package com.prince.finance.finsecure.DTO;

import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinancialRecordDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Type is required")
    private Type type;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @Size(max = 255, message = "Note cannot exceed 255 characters")
    private String note;
}