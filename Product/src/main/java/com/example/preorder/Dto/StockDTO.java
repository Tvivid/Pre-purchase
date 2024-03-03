package com.example.preorder.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDTO {

    private Long productId;
    private Long quantity;
}
