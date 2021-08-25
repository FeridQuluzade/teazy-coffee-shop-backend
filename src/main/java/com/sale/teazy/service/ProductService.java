package com.sale.teazy.service;

import com.sale.teazy.dto.ProductRequestDto;
import com.sale.teazy.dto.ProductResponseDto;

import java.util.List;


public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productCreateDto);

    ProductResponseDto findProductById(Long id);

    ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id);

    ProductResponseDto deleteProduct(Long id);

    List<ProductResponseDto> getAllProducts();

}
