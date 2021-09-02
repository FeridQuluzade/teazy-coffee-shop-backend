package com.sale.teazy.repository;

import com.sale.teazy.domain.Category;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
