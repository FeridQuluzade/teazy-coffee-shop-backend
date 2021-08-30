package com.sale.teazy.service;

import com.sale.teazy.dto.ProductRequestDto;
import com.sale.teazy.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productCreateDto);

    ProductResponseDto findProductById(Long id);

    ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id);

    ProductResponseDto deleteProduct(Long id);

    List<ProductResponseDto> getAllProducts();

    String uploadImage(MultipartFile multipartFile , Long id);

    String updateImage(MultipartFile multipartFile,Long id);

     void deleteFile(String fileName, String folder);
    void deleteProductImage(Long id);

    byte [] getFile(String fileName, String folder);

}
