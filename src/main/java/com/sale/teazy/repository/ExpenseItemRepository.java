package com.sale.teazy.repository;

import com.sale.teazy.domain.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem , Long> {
}
