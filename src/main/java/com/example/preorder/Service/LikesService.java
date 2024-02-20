package com.example.preorder.Service;

import com.example.preorder.Dto.LikesDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Comment;
import com.example.preorder.Entity.Likes;
import com.example.preorder.Entity.Member;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.BoardRepository;
import com.example.preorder.Repository.CommentRepository;
import com.example.preorder.Repository.LikesRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberLoginRepository memberLoginRepository;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;


    @Transactional
    public void likeTarget(String token, LikesDTO likesDTO){
        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));

        Likes likes = new Likes();
        likes.setMember(member);


        if(likesDTO.getBoardId()!=null) {
            Board board = boardRepository.findById(likesDTO.getBoardId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot find board"));
            likes.setBoard(board);
        }
        if(likesDTO.getCommentId()!=null) {
            Comment comment = commentRepository.findById(likesDTO.getCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot find comment"));
            likes.setComment(comment);
        }

        likesRepository.save(likes);

    }







}
