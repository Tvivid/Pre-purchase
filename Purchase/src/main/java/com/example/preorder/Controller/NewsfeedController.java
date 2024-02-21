package com.example.preorder.Controller;


import com.example.preorder.Dto.ActivityDTO;
import com.example.preorder.Entity.Activity;
import com.example.preorder.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class NewsfeedController {

    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/newsfeed")
    public ResponseEntity<List<Activity>> NewsFeed(@RequestHeader("Authorization") String accessToken){
        accessToken=accessToken.substring(7);
        List<Activity> activities = purchaseService.newsfeed(accessToken);
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/create")
    void createActivity(ActivityDTO activity){
        purchaseService.create(activity);
    };
}
