package com.example.preorder.Entity;

import com.example.preorder.Dto.StockDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    private Long productId;

    private Long stock;

    public static Stock toSaveEntity(StockDTO stockDTO){
        Stock stock = Stock.builder()
                .productId(stockDTO.getProductId())
                .stock(stockDTO.getQuantity())
                .build();
        return stock;
    }





}
