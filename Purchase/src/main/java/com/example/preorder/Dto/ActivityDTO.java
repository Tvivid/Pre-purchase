package com.example.preorder.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class ActivityDTO  {
    private String type; // 활동 타입 (예: Post, Comment, Like)
    private String userName; // 활동한 사용자 ID
    private String content; // 활동 내용
    private LocalDateTime createdAt; // 활동 생성 시간


}