package com.sale.teazy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleTypeRequestDto {
    @NotBlank
    @NotNull
    private String saleType;
    @Positive
    @NotNull
    private Double commission;

}
