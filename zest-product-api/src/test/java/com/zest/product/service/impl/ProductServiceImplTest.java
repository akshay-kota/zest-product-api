package com.zest.product.service.impl;

import com.zest.product.dto.ProductRequestDTO;
import com.zest.product.entity.Product;
import com.zest.product.exception.ResourceNotFoundException;
import com.zest.product.repository.ProductRepository;
import com.zest.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1)
                .productName("amul")
                .createdBy("ADMIN")
                .createdOn(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        var result = productService.getProductById(1);

        assertNotNull(result);
        assertEquals("amul", result.getProductName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(1));
    }

    @Test
    void testCreateProduct() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductName("kitkat");

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        var response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("amul", response.getProductName());
    }
}