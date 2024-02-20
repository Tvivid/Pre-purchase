package com.example.preorder.Service;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Dto.LikesDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Comment;
import com.example.preorder.Entity.Likes;
import com.example.preorder.Entity.Member;
import com.example.preorder.Feign.NewsFeedClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.BoardRepository;
import com.example.preorder.Repository.CommentRepository;
import com.example.preorder.Repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {


    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    private final UserFeignClient userFeignClient;

    private final NewsFeedClient newsFeedClient;


    @Transactional
    public void likeTarget(String token, LikesDTO likesDTO){
        Long member = userFeignClient.getMember(token);

        Likes likes = new Likes();
        likes.setMemberId(member);


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





        if(likesDTO.getBoardId()!=null){
            Board board = boardRepository.findById(likesDTO.getBoardId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot find board"));
            Activity activity = Activity.builder()
                    .type("Like")
                    .memberId(member)
                    .content(member + "가 " +board.getMemberId()+"의 글을 좋아합니다.")
                    .build();
            newsFeedClient.createActivity(activity);
        }
        else{
            Comment comment = commentRepository.findById(likesDTO.getCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot find comment"));
            Activity activity = Activity.builder()
                    .type("Like")
                    .memberId(member)
                    .content(member + "가 " +comment.getMemberId()+"의 댓글을 좋아합니다.")
                    .build();
            newsFeedClient.createActivity(activity);
        }





    }







}
