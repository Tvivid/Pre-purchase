package com.example.preorder.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardId")
    private Board board;

    @Column(name = "memberId")
    private Long memberId;

    @OneToMany(mappedBy = "comment")
    private Set<Likes> likesSet = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;




}
