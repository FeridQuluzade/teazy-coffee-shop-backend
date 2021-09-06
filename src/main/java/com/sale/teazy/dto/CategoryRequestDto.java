package com.sale.teazy.dto;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryRequestDto {
    @NotNull
    @NotBlank
    private String title;
}
