package com.sale.teazy.mapper;

import com.sale.teazy.domain.Product;

import com.sale.teazy.dto.ProductRequestDto;
import com.sale.teazy.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toProductDto(Product product);

    Product toProductEntity(ProductRequestDto productRequestDto);

    List<ProductResponseDto> toProductDtoList(List<Product> productList);
}
