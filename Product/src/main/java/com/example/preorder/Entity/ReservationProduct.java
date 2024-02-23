package com.example.preorder.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
public class ReservationProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    private Long price;

    private Long stock;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime reservation;

    @Column(name = "userId")
    private Long memberId; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.


    public void update(String title, String content, Long price, LocalDateTime reservation){
        this.title=title;
        this.content=content;
        this.price=price;
        this.reservation=reservation;

    }

    public void subStock(Long quantity){
        this.stock-=quantity;
    }

    public void addStock(Long quantity){
        this.stock+=quantity;
    }

}
