package com.example.preorder.Service;

import com.example.preorder.Entity.Follow;
import com.example.preorder.Entity.Member;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.FollowRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import com.example.preorder.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.rmi.server.LogStream.log;
import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberLoginRepository memberLoginRepository;

    @Transactional
    public void follow(String token, String followingEmail){
        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member follower = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));


        Member following = memberLoginRepository.findByEmail(followingEmail)
                .orElseThrow(() -> new IllegalArgumentException("not exist following user"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);



    }

    public void unfollow(Long followId) {
        followRepository.deleteById(followId);
    }




}
