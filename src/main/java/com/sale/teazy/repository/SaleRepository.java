package com.sale.teazy.repository;

import com.sale.teazy.domain.Product;
import com.sale.teazy.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByProductId(@Param("productId") Product productId);

    List<Sale> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
