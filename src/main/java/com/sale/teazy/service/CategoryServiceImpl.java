package com.sale.teazy.service;

import com.sale.teazy.domain.Category;
import com.sale.teazy.dto.CategoryRequestDto;
import com.sale.teazy.dto.CategoryResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.mapper.CategoryMapper;
import com.sale.teazy.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    protected Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, id));
    }

    @Override
    public List<CategoryResponseDto> createCategory(List<CategoryRequestDto> categoryRequestDtoList) {
        return categoryMapper
                .toCategoryDtoList(categoryRepository.saveAll(
                        categoryMapper.toCategoryEntityList(categoryRequestDtoList)));
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto, Long id) {
        Category category = getCategoryById(id);
        category.setTitle(categoryRequestDto.getTitle());
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDto> findAllCategory() {
        return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDto findCategoryById(Long id) {
        return categoryMapper.toCategoryDto(getCategoryById(id));
    }

    @Override
    public CategoryResponseDto deleteCategoryById(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.deleteById(id);
        return categoryMapper.toCategoryDto(category);
    }
}
