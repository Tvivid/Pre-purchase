package com.example.preorder.Service;


import com.example.preorder.Dto.CommentDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Comment;
import com.example.preorder.Entity.Member;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.BoardRepository;
import com.example.preorder.Repository.CommentRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberLoginRepository memberLoginRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;





    @Transactional
    public void save(String token, CommentDTO commentDTO){

        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));
        Board board = boardRepository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));

        Comment comment = new Comment();

        comment.setMember(member);
        comment.setContent(commentDTO.getContent());
        comment.setBoard(board);
        commentRepository.save(comment);


    }
}
