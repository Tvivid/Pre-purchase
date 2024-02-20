package com.example.preorder.Service;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Entity.*;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.MemberLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class NewsfeedService {
    @Autowired
    private MemberLoginRepository memberLoginRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public List<Activity> newsfeed(String token){

        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));

        Set<Follow> follows = member.getFollowers();
        List<Activity> activities = new ArrayList<>();
        System.out.println(follows);

        for (Follow follow : follows){

            Set<Board>boards=follow.getFollowing().getBoards();
            for(Board board:boards){
                activities.add(Activity.builder()
                        .type("Board")
                        .userName(follow.getFollowing().getUsername())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .build()
                       );
            }

            Set<Comment>comments=follow.getFollowing().getComments();
            for(Comment comment:comments){
                activities.add(Activity.builder()
                        .type("Comment")
                        .userName(follow.getFollowing().getUsername())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build()
                );
            }

            Set<Likes>likes=follow.getFollowing().getLikesSet();
            for(Likes like:likes){
                String likeContent;
                if(like.getBoard()!=null){
                    likeContent=like.getBoard().getMember().getUsername()+"님의 글을 좋아합니다.";
                }
                else{
                    likeContent=like.getComment().getMember().getUsername()+"님의 댓글을 좋아합니다.";
                }
                activities.add(Activity.builder()
                        .type("Likes")
                        .userName(follow.getFollowing().getUsername())
                        .content(likeContent)
                        .createdAt(like.getCreatedAt())
                        .build()
                );

            }

            Set<Follow> followSet=follow.getFollowing().getFollowers();
            for(Follow friend:followSet){
                System.out.println(friend.getFollower().getUsername());
                System.out.println(friend.getFollowing().getUsername());

                activities.add(Activity.builder()
                        .type("Follow")
                        .userName(follow.getFollowing().getUsername())
                        .content(friend.getFollowing().getUsername()+"을 팔로우합니다.")
                        .createdAt(friend.getCreatedAt())
                        .build()
                );
            }




        }

        Collections.sort(activities);
        return activities;

    }


}
