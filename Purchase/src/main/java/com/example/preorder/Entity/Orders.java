package com.example.preorder.Entity;

import com.example.preorder.Entity.type.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name="quantity")
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Orders(Long productId, Long memberId, Long quantity, PurchaseStatus status,
                  LocalDateTime deletedAt) {
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.status = status;
    }


    public void updateStatus(PurchaseStatus purchaseStatus){
        this.status=purchaseStatus;
    }

    public void cancel(){
        this.deletedAt=LocalDateTime.now();
    }




}
