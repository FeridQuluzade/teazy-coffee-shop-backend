package com.sale.teazy.controller;

import com.sale.teazy.dto.CategoryRequestDto;
import com.sale.teazy.dto.CategoryResponseDto;
import com.sale.teazy.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Find Category By ID")
    public ResponseEntity<CategoryResponseDto> findCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(categoryService.findCategoryById(id));
    }

    @GetMapping
    @ApiOperation("Find all Categories")
    public ResponseEntity<List<CategoryResponseDto>> findAllCategories(){
        return ResponseEntity.status(200).body(categoryService.findAllCategory());
    }

    @PostMapping
    @ApiOperation("Create Category")
    public ResponseEntity<List<CategoryResponseDto>> createCategories(@RequestBody List<CategoryRequestDto > categoryRequestDtoList){
        return ResponseEntity.status(201).body(categoryService.createCategory(categoryRequestDtoList));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update category by id ")
    public ResponseEntity<CategoryResponseDto> editCategory(@PathVariable("id")Long id,@RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.status(200).body(categoryService.updateCategory(categoryRequestDto,id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete category by id")
    public ResponseEntity<CategoryResponseDto> deleteCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.status(200).body(categoryService.deleteCategoryById(id));
    }

}
