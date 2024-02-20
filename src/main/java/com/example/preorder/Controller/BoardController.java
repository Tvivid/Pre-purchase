package com.example.preorder.Controller;

import com.example.preorder.Dto.BoardDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<String> BoardSave(@RequestHeader("Authorization") String accesstoken,@RequestBody BoardDTO boardDTO){
        accesstoken=accesstoken.substring(7);
        boardService.save(accesstoken, boardDTO);

        return ResponseEntity.ok("게시글이 업로드 되었습니다.");

    }


}
