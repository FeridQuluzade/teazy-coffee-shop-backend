package com.sale.teazy.service;

import com.sale.teazy.domain.Sale;
import com.sale.teazy.dto.SaleRequestDto;
import com.sale.teazy.dto.SaleResponseDto;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface SaleService {
    List<SaleResponseDto> createSale(List<SaleRequestDto> saleRequestDto);

    SaleResponseDto updateSale(SaleRequestDto saleRequestDto,Long id);

    SaleResponseDto findSaleById(Long id);

    SaleResponseDto deleteSaleById(Long id);

    List<SaleResponseDto> findSalesProductId(Long productId);

    List<SaleResponseDto> showSales(String startDate, String  endDate) throws ParseException;
}
