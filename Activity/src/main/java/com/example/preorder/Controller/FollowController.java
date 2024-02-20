package com.example.preorder.Controller;

import com.example.preorder.Dto.FollowDTO;
import com.example.preorder.Service.FollowService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class FollowController {


    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<Void> follow(@RequestHeader("Authorization") String accesstoken, @RequestBody FollowDTO followingEmail) {

        accesstoken=accesstoken.substring(7);
        followService.follow(accesstoken, followingEmail.getFollowing());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/follow/{followId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followId) {
        followService.unfollow(followId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/internal/following")
    public Set<Long> getFollowing(Long memberId){

        return followService.getFollowing(memberId);

    }



}
