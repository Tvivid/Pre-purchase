package com.example.preorder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Follow {

    @Id @GeneratedValue
    private Long id;



    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "following_id" )
    private Long followingId;


    @CreationTimestamp
    private LocalDateTime createdAt;
}
