package com.example.preorder.Entity;

import com.example.preorder.Entity.type.PurchaseStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Entity
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "memberId")
    private Long memberId;

    @Column(name="quantity")
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;




}
