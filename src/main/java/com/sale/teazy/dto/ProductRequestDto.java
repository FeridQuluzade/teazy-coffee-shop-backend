package com.sale.teazy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @NotBlank
    @NotNull
    private String title;
    @NotNull
    @NotBlank
    private String description;
    @Positive
    @NotNull
    private Double unitPrice;
}
