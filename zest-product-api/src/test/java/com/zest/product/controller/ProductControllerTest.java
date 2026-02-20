package com.zest.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zest.product.dto.ProductRequestDTO;
import com.zest.product.dto.ProductResponseDTO;
import com.zest.product.security.JwtAuthenticationFilter;
import com.zest.product.security.JwtUtil;
import com.zest.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetProductById() throws Exception {

        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1)
                .productName("amul")
                .createdBy("ADMIN")
                .createdOn(LocalDateTime.now())
                .build();

        when(productService.getProductById(1)).thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("amul"));
    }

    @Test
    void testCreateProduct() throws Exception {

        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductName("amul");

        ProductResponseDTO response = ProductResponseDTO.builder()
                .id(1)
                .productName("amul")
                .createdBy("ADMIN")
                .createdOn(LocalDateTime.now())
                .build();

        when(productService.createProduct(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("amul"));
    }
}