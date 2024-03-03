package com.example.preorder.Entity;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    private Long price;

    private Long stock;

    private boolean reserved;

    private LocalDateTime reservation;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "member_id")
    private Long memberId; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.


    public void update(String title, String content, Long price){
        this.title=title;
        this.content=content;
        this.price=price;
    }

    public void subStock(Long quantity){
        this.stock-=quantity;
    }

    public void addStock(Long quantity){
        this.stock+=quantity;
    }


}