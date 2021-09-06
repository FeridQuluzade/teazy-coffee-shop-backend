package com.sale.teazy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleTypeRequestDto {
    private String saleType;
    private Double commission;

}
