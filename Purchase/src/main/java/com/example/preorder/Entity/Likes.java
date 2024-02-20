package com.example.preorder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Likes {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "memberId")
    private Long memberId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
