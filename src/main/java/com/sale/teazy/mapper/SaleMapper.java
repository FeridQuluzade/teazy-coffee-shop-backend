package com.sale.teazy.mapper;

import com.sale.teazy.domain.Product;
import com.sale.teazy.domain.Sale;
import com.sale.teazy.domain.SaleType;
import com.sale.teazy.dto.SaleRequestDto;
import com.sale.teazy.dto.SaleResponseDto;
import com.sale.teazy.repository.ProductRepository;
import com.sale.teazy.repository.SaleTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = ProductMapper.class)
public abstract class SaleMapper {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleTypeRepository saleTypeRepository;


    @Mapping(target = "productId", source = "productId")
    public abstract Sale toSaleEntity(SaleRequestDto saleRequestDto);


    public abstract List<SaleResponseDto> toSaleEntity(List<Sale> saleResponseDtos);
    protected Product fromLongToProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    protected Long fromProductToLong(Product product) {
        return product.getId();
    }

    protected SaleType fromLongToSaleType(Long saleType) {
        return saleTypeRepository.findById(saleType)
                .orElseThrow();
    }

    protected Long fromSaleTypeToLong(SaleType sale) {
        return sale.getId();
    }

    @Mapping(target = "productId", source = "productId")
    public abstract SaleResponseDto toSaleDto(Sale sale);

}
