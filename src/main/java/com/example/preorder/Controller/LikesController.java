package com.example.preorder.Controller;

import com.example.preorder.Dto.LikesDTO;
import com.example.preorder.Service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/like-post")
@RequiredArgsConstructor
public class LikesController {

    @Autowired
    private LikesService likesService;



    @PostMapping
    public ResponseEntity<String> likeTarget(@RequestHeader("Authorization") String accessToken, @RequestBody LikesDTO likesdto){
        accessToken=accessToken.substring(7);
        likesService.likeTarget(accessToken, likesdto);
        return ResponseEntity.ok("좋아요를 눌렀습니다.");
    }
}
