package com.sale.teazy.repository;

import com.sale.teazy.domain.Product;
import com.sale.teazy.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByProductId(@Param("productId") Product productId);
}
