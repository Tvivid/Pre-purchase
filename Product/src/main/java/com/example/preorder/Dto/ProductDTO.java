package com.example.preorder.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {

    private String title;
    private String content;

    private Long price;

    private boolean reserved;
    private LocalDateTime reservation;

    private Long stock;
}
