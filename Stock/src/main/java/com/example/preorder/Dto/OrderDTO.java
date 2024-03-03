package com.example.preorder.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDTO {

    private Long productId;
    private Long quantity;

}