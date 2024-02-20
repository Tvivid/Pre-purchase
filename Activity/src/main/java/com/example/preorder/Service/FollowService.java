package com.example.preorder.Service;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Entity.Follow;
import com.example.preorder.Entity.Member;
import com.example.preorder.Feign.NewsFeedClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.FollowRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserFeignClient userFeignClient;

    private final MemberLoginRepository memberLoginRepository;

    private final NewsFeedClient newsFeedClient;


    @Transactional
    public void follow(String token, String followingEmail){
        Long follower = userFeignClient.getMember(token);


        Member following = memberLoginRepository.findByEmail(followingEmail)
                .orElseThrow(() -> new IllegalArgumentException("not exist following user"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following.getId());
        followRepository.save(follow);

        Activity activity = Activity.builder()
                .type("Comment")
                .memberId(follower)
                .content(follower + "가 " +following.getId()+"를 팔로우 합니다.")
                .build();

        newsFeedClient.createActivity(activity);




    }

    @Transactional
    public void unfollow(Long followId) {
        followRepository.deleteById(followId);
    }


    @Transactional
    public Set<Long> getFollowing(Long memberId){
        Set<Long> followList = followRepository.findFollowingIdsByFollowerId(memberId);

        return followList;
    }




}
