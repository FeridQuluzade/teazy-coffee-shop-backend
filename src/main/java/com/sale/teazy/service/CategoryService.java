package com.sale.teazy.service;

import com.sale.teazy.dto.CategoryRequestDto;
import com.sale.teazy.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> createCategory(List<CategoryRequestDto> categoryRequestDtoList);

    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto , Long id);

    List<CategoryResponseDto> findAllCategory();

    CategoryResponseDto findCategoryById(Long id);

    CategoryResponseDto deleteCategoryById(Long id);


}
