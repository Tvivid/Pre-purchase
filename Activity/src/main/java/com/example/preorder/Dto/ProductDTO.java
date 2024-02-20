package com.example.preorder.Dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String title;
    private String content;

    private Long price;

    private Long stock;
}
