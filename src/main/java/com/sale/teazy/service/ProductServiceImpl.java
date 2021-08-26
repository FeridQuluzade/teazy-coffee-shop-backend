package com.sale.teazy.service;

import com.sale.teazy.domain.Product;
import com.sale.teazy.dto.ProductRequestDto;
import com.sale.teazy.dto.ProductResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.mapper.ProductMapper;
import com.sale.teazy.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static net.logstash.logback.argument.StructuredArguments.kv;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    protected Product getProductById(Long id) {
        log.info("product findById started with : {}", kv("id",id));
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productCreateDto) {
        log.info("Product create started with : {}" , kv("productCreateDto",productCreateDto));

        Product product = productRepository
                .save(productMapper
                        .toProductEntity(productCreateDto));
        ProductResponseDto productResponseDto= productMapper.toProductDto(product);

        log.info(" created Product with : {}" , kv("productResponseDto",productResponseDto));

        return productResponseDto;
    }

    @Override
    public ProductResponseDto findProductById(Long id) {

        return productMapper
                .toProductDto(getProductById(id));
    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id) {
        getProductById(id);

        Product product = productMapper.
                toProductEntity(productRequestDto);
        product.setId(id);

        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productMapper.toProductDtoList(productRepository.findAll());
    }

    @Override
    public ProductResponseDto deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.deleteById(id);

        return productMapper.toProductDto(product);
    }
}
