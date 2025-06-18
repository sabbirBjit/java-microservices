package com.sabbir.microservice.product.service;

import com.sabbir.microservice.product.dto.ProductRequest;
import com.sabbir.microservice.product.dto.ProductResponse;
import com.sabbir.microservice.product.model.Product;
import com.sabbir.microservice.product.repository.ProductRepository;
import cool.graph.cuid.Cuid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(Cuid.createCuid())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price() != null ? productRequest.price() : BigDecimal.ZERO)
                .build();
        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(
                        product -> new ProductResponse(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice()
                        )
                ).toList();
    }
}
