package com.example.preorder.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    private String type; // 활동 타입 (예: Post, Comment, Like)
    private String userName; // 활동한 사용자 ID
    private String content; // 활동 내용
    private LocalDateTime createdAt; // 활동 생성 시간


}
