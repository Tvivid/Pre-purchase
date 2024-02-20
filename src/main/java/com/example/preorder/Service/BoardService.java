package com.example.preorder.Service;

import com.example.preorder.Dto.BoardDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Member;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.BoardRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberLoginRepository memberLoginRepository;


    @Transactional
    public void save(String token, BoardDTO boardDTO){

        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));

        String title=boardDTO.getTitle();
        String content= boardDTO.getContent();
        System.out.println(title);
        System.out.println(content);
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setMember(member);
        boardRepository.save(board);
    }

}
