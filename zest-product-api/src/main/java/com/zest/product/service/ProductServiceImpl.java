package com.zest.product.service;

import com.zest.product.dto.ItemResponseDTO;
import com.zest.product.dto.ProductRequestDTO;
import com.zest.product.dto.ProductResponseDTO;
import com.zest.product.entity.Product;
import com.zest.product.exception.ResourceNotFoundException;
import com.zest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Product product = Product.builder()
                .productName(dto.getProductName())
                .createdBy("ADMIN")
                .createdOn(LocalDateTime.now())
                .build();

        Product saved = productRepository.save(product);

        return mapToDTO(saved);
    }

    @Override
    public ProductResponseDTO getProductById(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToDTO(product);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {

      return productRepository.findAll(pageable)
              .map(this::mapToDTO);
    }

    @Override
    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setProductName(dto.getProductName());
        product.setModifiedBy("ADMIN");
        product.setModifiedOn(LocalDateTime.now());

        return mapToDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public List<ItemResponseDTO> getItemsByProductId(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return product.getItems()
                .stream()
                .map(item -> ItemResponseDTO.builder().id(item.getId())
                        .quantity(item.getQuantity())
                        .build()).toList();
    }

    private ProductResponseDTO mapToDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .build();
    }
}
