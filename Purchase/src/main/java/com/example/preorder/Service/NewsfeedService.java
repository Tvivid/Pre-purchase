package com.example.preorder.Service;


import com.example.preorder.Dto.ActivityDTO;
import com.example.preorder.Entity.*;
import com.example.preorder.Feign.ActivityFeignClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NewsfeedService {


    private UserFeignClient userFeignClient;
    private ActivityFeignClient activityFeignClient;

    private ActivityRepository activityRepository;

    public List<Activity> newsfeed(String token){


        Long memberId = userFeignClient.getMember(token);

        Set<Long> follows = activityFeignClient.getFollowing(memberId);


        List<Activity> activities = activityRepository.findByUserNames(follows);

        return activities;

    }


    public void create(ActivityDTO activityDTO){
        Activity activity = new Activity();

        activity.setType(activityDTO.getType());
        activity.setContent(activityDTO.getContent());
        activity.setUserName(activityDTO.getUserName());
        activity.setCreatedAt(activityDTO.getCreatedAt());

        activityRepository.save(activity);
    }





}
