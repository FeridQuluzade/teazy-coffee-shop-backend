package com.sale.teazy.repository;

import com.sale.teazy.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense ,Long> {
}
