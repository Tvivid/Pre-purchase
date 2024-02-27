package com.example.preorder.Dto;

import com.example.preorder.Entity.Orders;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDTO {

    private Long productId;
    private Long quantity;


    public static OrderDTO entityToOrderDTO(Orders orders){
        return OrderDTO.builder()
                .productId(orders.getProductId())
                .quantity(orders.getQuantity())
                .build();
    }
}
