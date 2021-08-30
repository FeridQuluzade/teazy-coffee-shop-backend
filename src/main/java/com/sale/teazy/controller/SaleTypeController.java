package com.sale.teazy.controller;


import com.sale.teazy.dto.SaleTypeRequestDto;
import com.sale.teazy.dto.SaleTypeResponseDto;
import com.sale.teazy.service.SaleTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/saleTypes")
public class SaleTypeController {
    private final SaleTypeService saleTypeService;

    public SaleTypeController(SaleTypeService saleTypeService) {
        this.saleTypeService = saleTypeService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Find SaleType by Id")
    public ResponseEntity<SaleTypeResponseDto> findSaleTypeById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(saleTypeService.findSaleTypeById(id));
    }

    @GetMapping
    @ApiOperation("Find saleType list ")
    public ResponseEntity<List<SaleTypeResponseDto>> findSaleTypeList() {
        return ResponseEntity.status(200).body(saleTypeService.findAllSaleTypes());
    }

    @PostMapping
    @ApiOperation("Add saleType ")
    public ResponseEntity<SaleTypeResponseDto> createSaleType(@RequestBody SaleTypeRequestDto saleRequestDto) {
        return ResponseEntity.status(201).body(saleTypeService.createSaleType(saleRequestDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete saleType By Id")
    public ResponseEntity<SaleTypeResponseDto> deleteSaleTypeById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(saleTypeService.deleteSaleType(id));
    }

    @PutMapping("/{id]")
    @ApiOperation("Edit saleType By Id")
    public ResponseEntity<SaleTypeResponseDto> editSaleTypeById(@PathVariable("id") Long id,
                                                            @RequestBody SaleTypeRequestDto saleTypeRequestDto) {
        return ResponseEntity.status(200).body(saleTypeService.updateSaleType(saleTypeRequestDto, id));
    }

}
