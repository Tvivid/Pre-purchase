package com.example.preorder.Controller;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/newsfeed")
public class NewsfeedController {

    @Autowired
    private NewsfeedService newsfeedService;


    @GetMapping
    public ResponseEntity<List<Activity>> NewsFeed(@RequestHeader("Authorization") String accessToken){
        accessToken=accessToken.substring(7);
        List<Activity> activities = newsfeedService.newsfeed(accessToken);
        return ResponseEntity.ok(activities);
    }
}
