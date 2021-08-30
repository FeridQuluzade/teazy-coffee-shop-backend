package com.sale.teazy.controller;

import com.sale.teazy.dto.ProductRequestDto;
import com.sale.teazy.dto.ProductResponseDto;
import com.sale.teazy.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Find product by Id")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(productService.findProductById(id));
    }

    @GetMapping
    @ApiOperation("Find all products")
    public ResponseEntity<List<ProductResponseDto>> findAllProducts() {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @PostMapping()
    @ApiOperation("Add Product")
    public ResponseEntity<ProductResponseDto> createProduct( @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.status(201).body(productService.createProduct(productRequestDto));
    }

    @PutMapping("/{id}")
    @ApiOperation("Edit Product by Id")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity.status(200).body(productService.updateProduct(productRequestDto, id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Product by Id")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(productService.deleteProduct(id));
    }

    @PostMapping("/image/{id}")
    @ApiOperation(value = "Add product file")
    public ResponseEntity<String> createImage(@PathVariable("id") Long id,
                                              @Valid @RequestParam MultipartFile multipartFile){
        return ResponseEntity.status(200).body(productService.uploadImage(multipartFile,id));
    }

    @DeleteMapping("/image/{id}")
    @ApiOperation(value =  "Delete Product file")
    public void deleteProductFile( @PathVariable  Long id){
        productService.deleteProductImage(id);
    }


}
