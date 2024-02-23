package com.example.preorder.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationProductDTO {

    private String title;

    private String content;

    private Long price;

    private Long stock;

    private LocalDateTime reservation;
}
