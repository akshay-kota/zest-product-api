package com.zest.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponseDTO {

    private Integer id;
    private String productName;
    private String createdBy;
    private LocalDateTime createdOn;
}
