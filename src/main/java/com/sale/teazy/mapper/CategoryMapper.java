package com.sale.teazy.mapper;

import com.sale.teazy.domain.Category;
import com.sale.teazy.dto.CategoryRequestDto;
import com.sale.teazy.dto.CategoryResponseDto;
import com.sale.teazy.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toCategoryDto(Category category);

    Category toCategoryEntity(CategoryRequestDto categoryRequestDto);

    List<Category> toCategoryEntityList(List<CategoryRequestDto> categoryRequestDtoList);

}
