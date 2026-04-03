package com.prince.finance.finsecure.repository;

import com.prince.finance.finsecure.entities.Financial_record;
import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<Financial_record,Long> {
    List<Financial_record> findByType(Type type);
    List<Financial_record> findByUserId(Long id);
    List<Financial_record> findByCategory(Category category);
    List<Financial_record> findByDateBetween(
            LocalDateTime start, LocalDateTime end);
}


