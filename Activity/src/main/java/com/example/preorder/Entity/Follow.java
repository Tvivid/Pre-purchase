package com.example.preorder.Entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Follow {

    @Id @GeneratedValue
    private Long id;


    @Column(name = "followerId")
    private Long follower;


    @Column(name = "followingId")
    private Long following;


    @CreationTimestamp
    private LocalDateTime createdAt;
}
