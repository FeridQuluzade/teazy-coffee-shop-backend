package com.sale.teazy.mapper;

import com.sale.teazy.domain.Product;
import com.sale.teazy.domain.Sale;
import com.sale.teazy.dto.SaleRequestDto;
import com.sale.teazy.dto.SaleResponseDto;
import com.sale.teazy.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = ProductMapper.class)
public abstract class SaleMapper {
    @Autowired
    private ProductRepository productRepository;

    @Mapping(target = "productId", source = "productId")
    public abstract Sale toSaleEntity(SaleRequestDto saleRequestDto);

    protected Product fromLongToProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    protected Long fromProductToLong(Product product) {
        return product.getId();
    }

    @Mapping(target = "productId", source = "productId")
    public abstract SaleResponseDto toSaleDto(Sale sale);

}
