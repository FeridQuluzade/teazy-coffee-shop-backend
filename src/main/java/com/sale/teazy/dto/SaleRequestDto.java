package com.sale.teazy.dto;

import com.sale.teazy.domain.SaleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleRequestDto {
    @Positive
    @NotNull
    private Long productId;
    @Positive
    @NotNull
    private Long amount;
    @NotNull
    @NotBlank
    private Long saleType;

    private MultipartFile multipartFile;
}
