package com.sale.teazy.service;

import com.sale.teazy.dto.SaleResponseDto;
import com.sale.teazy.dto.SaleTypeRequestDto;
import com.sale.teazy.dto.SaleTypeResponseDto;

import java.util.List;

public interface SaleTypeService {

  SaleTypeResponseDto createSaleType(SaleTypeRequestDto saleTypeRequestDto);

  SaleTypeResponseDto findSaleTypeById(Long id);

  SaleTypeResponseDto updateSaleType(SaleTypeRequestDto saleTypeRequestDto,Long id);

  SaleTypeResponseDto deleteSaleType(Long id);

  List<SaleTypeResponseDto > getAllSaleTypes();



}
