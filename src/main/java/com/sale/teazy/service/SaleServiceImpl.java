package com.sale.teazy.service;

import com.sale.teazy.domain.Product;
import com.sale.teazy.domain.Sale;
import com.sale.teazy.dto.ProductResponseDto;
import com.sale.teazy.dto.SaleRequestDto;
import com.sale.teazy.dto.SaleResponseDto;
import com.sale.teazy.dto.SaleTypeResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.mapper.SaleMapper;
import com.sale.teazy.repository.SaleRepository;
import com.sale.teazy.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService {
    private final ProductServiceImpl productService;
    private final SaleTypeService saleTypeService;
    private final DateUtil dateUtil;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository,
                           SaleMapper saleMapper,
                           ProductServiceImpl productService,
                           SaleTypeService saleTypeService,
                           DateUtil dateUtil) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.productService = productService;
        this.saleTypeService = saleTypeService;
        this.dateUtil = dateUtil;
    }

    protected Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(Sale.class, id));
    }

    @Override
    public List<SaleResponseDto> createSale(List<SaleRequestDto> saleRequestDto) {
        List<Sale> arr = new ArrayList<>();

        for (SaleRequestDto s : saleRequestDto) {

            ProductResponseDto product = productService.findProductById(s.getProductId());
            SaleTypeResponseDto saleType = saleTypeService.findSaleTypeById(s.getSaleType());

            Sale nonAmountSale = saleMapper.toSaleEntity(s);

            nonAmountSale.setPrice(product.getUnitPrice() * nonAmountSale.getAmount());
            nonAmountSale
                    .setCommissionPrice(
                            (product.getUnitPrice() * (100 + saleType.getCommission()) / 100)
                                    * (nonAmountSale.getAmount()));
            nonAmountSale.setSaleTypeValue(saleType.getSaleType());

            arr.add(nonAmountSale);
        }
        saleRepository.saveAll(arr);

        return saleMapper.toSaleEntity(arr);
    }

    @Override
    public SaleResponseDto updateSale(SaleRequestDto saleRequestDto, Long id) {
        ProductResponseDto product = productService.findProductById(saleRequestDto.getProductId());
        SaleTypeResponseDto saleType = saleTypeService.findSaleTypeById(saleRequestDto.getSaleType());
        Sale sale = getSaleById(id);
//        sale.setSaleType(saleRequestDto.getSaleType());
        sale.setAmount(saleRequestDto.getAmount());
        sale.setPrice(saleRequestDto.getAmount() * product.getUnitPrice());
        sale.setCommissionPrice(
                (product.getUnitPrice() * (100 + saleType.getCommission()) / 100)
                        * saleRequestDto.getAmount());
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
        Product product = productService.getProductById(productId);

        List<SaleResponseDto> saleResponseDtoList = new ArrayList<>();

        List<Sale> sales = saleRepository.findByProductId(product);
        for (Sale s : sales) {
            SaleResponseDto saleResponseDto = saleMapper.toSaleDto(s);
            saleResponseDtoList.add(saleResponseDto);
        }
        return saleResponseDtoList;
    }

    @Override
    @SneakyThrows
    public List<SaleResponseDto> showSales(String startDate, String endDate)  {
        List<LocalDateTime> dates=dateUtil.getDateIfAcceptable(startDate,endDate);
        if (!saleMapper.toSaleEntity(saleRepository.findByCreatedAtBetween(dates.get(0), dates.get(1))).isEmpty()){
            return saleMapper.toSaleEntity(saleRepository.findByCreatedAtBetween(dates.get(0), dates.get(1)));
        }
        else throw new EntityNotFoundException(Sale.class);
    }
}
