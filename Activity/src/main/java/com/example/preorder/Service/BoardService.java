package com.example.preorder.Service;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Dto.BoardDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Member;
import com.example.preorder.Feign.NewsFeedClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserFeignClient userFeignClient;

    private final NewsFeedClient newsFeedClient;


    @Transactional
    public void save(String token, BoardDTO boardDTO){

        Long member = userFeignClient.getMember(token);

        String title=boardDTO.getTitle();
        String content= boardDTO.getContent();
        System.out.println(title);
        System.out.println(content);
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setMemberId(member);
        boardRepository.save(board);

        Activity activity = Activity.builder()
                        .type("Post")
                                .memberId(member)
                                        .content(content)
                                                .build();

        newsFeedClient.createActivity(activity);
    }

}
