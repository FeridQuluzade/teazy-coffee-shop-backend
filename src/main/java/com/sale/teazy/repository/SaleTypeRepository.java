package com.sale.teazy.repository;

import com.sale.teazy.domain.SaleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleTypeRepository extends JpaRepository<SaleType, Long> {
    SaleType findBySaleType(String saleType);
}
