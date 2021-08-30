package com.sale.teazy.service;

import com.sale.teazy.domain.SaleType;
import com.sale.teazy.dto.SaleTypeRequestDto;
import com.sale.teazy.dto.SaleTypeResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.mapper.SaleTypeMapper;
import com.sale.teazy.repository.SaleTypeRepository;
import com.sale.teazy.util.SaleTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Slf4j
public class SaleTypeServiceImpl implements SaleTypeService {

    private final SaleTypeRepository saleTypeRepository;
    private final SaleTypeUtil saleTypeUtil;
    private final SaleTypeMapper saleTypeMapper;

    public SaleTypeServiceImpl(SaleTypeRepository saleTypeRepository,
                               SaleTypeUtil saleTypeUtil,
                               SaleTypeMapper saleTypeMapper) {
        this.saleTypeRepository = saleTypeRepository;
        this.saleTypeUtil = saleTypeUtil;
        this.saleTypeMapper = saleTypeMapper;
    }

    protected SaleType getSaleTypeById(Long id) {
        log.info("saleType findById started with : {}", kv("id", id));
        return saleTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SaleType.class, id));
    }

    @Override
    public SaleTypeResponseDto createSaleType(SaleTypeRequestDto saleTypeRequestDto) {
        String acceptable = saleTypeUtil.getSaleTypeIfAcceptable(saleTypeRequestDto.getSaleType());

        log.info("saleType create started with : {}", kv("SaleTypeRequestDto", saleTypeRequestDto));
        saleTypeRequestDto.setSaleType(acceptable);

        SaleType saleType = saleTypeRepository.save(
                saleTypeMapper.toSaleTypeEntity(saleTypeRequestDto)
        );

        SaleTypeResponseDto saleTypeResponseDto = saleTypeMapper.toSaleTypeDto(saleType);

        log.info(" created SaleType with : {}", kv("saleTypeResponseDto", saleTypeResponseDto));

        return saleTypeResponseDto;
    }

    @Override
    public SaleTypeResponseDto findSaleTypeById(Long id) {
        return saleTypeMapper.toSaleTypeDto(getSaleTypeById(id));
    }

    @Override
    public SaleTypeResponseDto updateSaleType(SaleTypeRequestDto saleTypeRequestDto, Long id) {
        return null;
    }

    @Override
    public SaleTypeResponseDto deleteSaleType(Long id) {
        SaleType saleType = getSaleTypeById(id);
        saleTypeRepository.deleteById(id);
        return saleTypeMapper.toSaleTypeDto(saleType);
    }

    @Override
    public List<SaleTypeResponseDto> getAllSaleTypes() {
        return saleTypeMapper.toSaleResponseDtoList(saleTypeRepository.findAll());
    }
}
