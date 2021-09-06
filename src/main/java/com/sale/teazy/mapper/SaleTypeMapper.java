package com.sale.teazy.mapper;
import com.sale.teazy.domain.Sale;
import com.sale.teazy.domain.SaleType;
import com.sale.teazy.dto.SaleTypeRequestDto;
import com.sale.teazy.dto.SaleTypeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface SaleTypeMapper {
   SaleTypeResponseDto toSaleTypeDto(SaleType saleType);

   SaleType toSaleTypeEntity(SaleTypeRequestDto saleTypeRequestDto);

   List<SaleTypeResponseDto> toSaleResponseDtoList(List<SaleType> saleTypeList);

}
