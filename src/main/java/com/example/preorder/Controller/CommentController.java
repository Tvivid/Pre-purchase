package com.example.preorder.Controller;


import com.example.preorder.Dto.CommentDTO;
import com.example.preorder.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<String> commentsave(@RequestHeader("Authorization") String accesstoken, @RequestBody CommentDTO commentDTO){
        accesstoken=accesstoken.substring(7);
        commentService.save(accesstoken,commentDTO);
        return ResponseEntity.ok("댓글이 업로드 되었습니다.");
    }

}
