package com.sale.teazy.service;

import com.sale.teazy.domain.Product;
import com.sale.teazy.domain.Sale;
import com.sale.teazy.dto.ProductResponseDto;
import com.sale.teazy.dto.SaleRequestDto;
import com.sale.teazy.dto.SaleResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.mapper.ProductMapper;
import com.sale.teazy.mapper.SaleMapper;
import com.sale.teazy.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final ProductServiceImpl productService;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository,
                           SaleMapper saleMapper,
                           ProductServiceImpl productService) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.productService = productService;
    }

    protected Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(Sale.class, id));
    }

    @Override
    public List<SaleResponseDto> createSale(List<SaleRequestDto> saleRequestDto) {
        List<SaleResponseDto> saleResponseDtos = new ArrayList<>();

        for (SaleRequestDto s : saleRequestDto) {
            ProductResponseDto product = productService.findProductById(s.getProductId());

            Sale nonAmountSale = saleMapper.toSaleEntity(s);

            nonAmountSale.setPrice(product.getUnitPrice() * nonAmountSale.getAmount());

            saleResponseDtos.add(saleMapper.toSaleDto(saleRepository.save(nonAmountSale)));
        }

        return saleResponseDtos;
    }

    @Override
    public SaleResponseDto updateSale(SaleRequestDto saleRequestDto, Long id) {
        ProductResponseDto product = productService.findProductById(saleRequestDto.getProductId());
        Sale sale = getSaleById(id);
//        sale.setSaleType(saleRequestDto.getSaleType());
        sale.setAmount(saleRequestDto.getAmount());
        sale.setPrice(saleRequestDto.getAmount() * product.getUnitPrice());
        //sale.setProductId();
        //TODO:sale product idsni deyismek
        saleRepository.save(sale);
        return saleMapper.toSaleDto(sale);
    }

    @Override
    public SaleResponseDto findSaleById(Long id) {
        return saleMapper.
                toSaleDto(getSaleById(id));
    }

    @Override
    public SaleResponseDto deleteSaleById(Long id) {
        Sale sale = getSaleById(id);
        saleRepository.delete(sale);
        return saleMapper.toSaleDto(sale);
    }

    @Override
    public List<SaleResponseDto> findSalesProductId(Long productId) {
        Product product=productService.getProductById(productId);

        List<SaleResponseDto> saleResponseDtoList = new ArrayList<>();

        List<Sale> sales= saleRepository.findByProductId(product);
        for (Sale s : sales) {
            SaleResponseDto saleResponseDto = saleMapper.toSaleDto(s);
            saleResponseDtoList.add(saleResponseDto);
        }
        return saleResponseDtoList;
    }
}
