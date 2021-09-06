package com.sale.teazy.service;

import com.sale.teazy.domain.Product;
import com.sale.teazy.dto.ProductRequestDto;
import com.sale.teazy.dto.ProductResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.exception.FileCantUploadException;
import com.sale.teazy.mapper.ProductMapper;
import com.sale.teazy.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final FileServiceImpl fileService;
    private final String imageFolder;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              FileServiceImpl fileService,
                              @Value("${minio.image-folder}") String imageFolder) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.fileService = fileService;
        this.imageFolder = imageFolder;
    }

    protected Product getProductById(Long id) {
        log.info("product findById started with : {}", kv("id", id));
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        log.info("Product create started with : {}", kv("productCreateDto", productRequestDto));
        Product product = productRepository
                .save(productMapper
                        .toProductEntity(productRequestDto));

//        productRepository.save(product);
        ProductResponseDto productResponseDto = productMapper.toProductDto(product);

        log.info(" created Product with : {}", kv("productResponseDto", productResponseDto));

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

    @Override
    @Transactional
    public String uploadImage(MultipartFile multipartFile, Long id) {
        Product product = getProductById(id);
        if (product.getImg() == null) {
            String fileName = fileService.uploadImage(multipartFile, imageFolder);
            product.setImg(fileName);
            productRepository.save(product);
            return fileName;
        }
        throw new FileCantUploadException(multipartFile.getOriginalFilename());
    }

    @Override
    @Transactional
    public String updateImage(MultipartFile multipartFile, Long id) {
        log.info("UpdateImage to User started with , {}",
                kv("partnerId", id));
        Product product = getProductById(id);
        deleteFile(product.getImg(), imageFolder);
        String fileName = fileService.uploadImage(multipartFile, imageFolder);
        product.setImg(fileName);
        productRepository.save(product);
        log.info("updateImage to product completed successfully with {}",
                kv("partnerId", product));
        return fileName;
    }

    @Override
    @Transactional
    public void deleteFile(String fileName, String folder) {
        log.info("deleteFile started from Product with {}", kv("fileName", fileName));
        fileService.deleteFile(fileName, folder);
        log.info("deleteFile completed successfully from Product with {} ", kv("fileName", fileName));
    }

    @Override
    public void deleteProductImage(Long id) {
        log.info("delete product image started from Product with {}", kv("id", id));
        Product product = getProductById(id);
        if (product.getImg() != null) {
            fileService.deleteFile(product.getImg(), imageFolder);
            product.setImg(null);
            productRepository.save(product);
        }
        log.info("deleteProductImage completed successfully from Product with {}", kv("id", id));
    }

    @Override
    public byte[] getFile(String fileName, String folder) {
        log.info("getFile started with {} ", kv("fileName", fileName));
        return fileService.getFile(fileName, folder);
    }
}
