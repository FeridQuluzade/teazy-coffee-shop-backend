package com.sale.teazy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleTypeResponseDto {
    private Long id;
    private String saleType;
    private Double commission;
}
