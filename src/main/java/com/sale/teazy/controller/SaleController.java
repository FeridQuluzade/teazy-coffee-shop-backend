package com.sale.teazy.controller;

import com.sale.teazy.dto.SaleRequestDto;
import com.sale.teazy.dto.SaleResponseDto;
import com.sale.teazy.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Find sale by Id")
    public ResponseEntity<SaleResponseDto> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(saleService.findSaleById(id));
    }

    @GetMapping
    @ApiOperation("Find sales list by Product id")
    public ResponseEntity<List<SaleResponseDto>> findSalesListByProductId(@RequestParam("productId") Long productId) {
        return ResponseEntity.status(200).body(saleService.findSalesProductId(productId));
    }

    @PostMapping
    @ApiOperation("Add Sale")
    public ResponseEntity<List<SaleResponseDto>> createSales(@RequestBody List<@Valid SaleRequestDto> saleRequestDtoList) {
        return ResponseEntity.status(201).body(saleService.createSale(saleRequestDtoList));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete sale By Id")
    public ResponseEntity<SaleResponseDto> deleteSaleById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(saleService.deleteSaleById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Edit sale By Id")
    public ResponseEntity<SaleResponseDto> editSaleById(@PathVariable("id") Long id,
                                                        @RequestBody @Valid SaleRequestDto saleRequestDto) {
        return ResponseEntity.status(200).body(saleService.updateSale(saleRequestDto, id));
    }
}
