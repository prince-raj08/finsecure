package com.prince.finance.finsecure.DTO;

import com.prince.finance.finsecure.enums.Category;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CategorySummaryDto {
    private Category category;
    private BigDecimal totalAmount;
}