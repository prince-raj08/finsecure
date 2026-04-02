package com.prince.finance.finsecure.repository;

import com.prince.finance.finsecure.entities.Financial_record;
import com.prince.finance.finsecure.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<Financial_record,Long> {

    List<Financial_record> findByUserId(Long userId);


    @Query("SELECT SUM(f.amount) FROM Financial_record f WHERE f.user.id = :userId AND f.type = :type")
    BigDecimal sumAmountByUserIdAndType(@Param("userId") Long userId, @Param("type") Type type);
}


