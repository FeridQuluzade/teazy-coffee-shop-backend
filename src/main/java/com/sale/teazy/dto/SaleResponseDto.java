package com.sale.teazy.dto;

import com.sale.teazy.domain.SaleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponseDto {
    private Long id;
    private Long productId;
    private Long amount;
    private Double price;
    private Long saleType;

}
