package com.example.preorder.Service;


import com.example.preorder.Dto.ActivityDTO;
import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Entity.*;
import com.example.preorder.Entity.type.PurchaseStatus;
import com.example.preorder.Feign.ActivityFeignClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.ActivityRepository;
import com.example.preorder.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private OrderRepository orderRepository;
    private UserFeignClient userFeignClient;
    private ActivityFeignClient activityFeignClient;

    private ActivityRepository activityRepository;

    public List<Activity> newsfeed(String token){


        Long memberId = userFeignClient.getMember(token);

        Set<Long> follows = activityFeignClient.getFollowing(memberId);


        List<Activity> activities = activityRepository.findByUserNames(follows);

        return activities;

    }


    public void create(String token, OrderDTO orderDTO){

        Long memberId = userFeignClient.getMember(token);


        Order order = Order.builder()
                        .memberId(memberId)
                                .productId(orderDTO.getProductId())
                                        .quantity(orderDTO.getQuantity())
                                                .status(PurchaseStatus.processing)
                                                        .build();
        orderRepository.save(order);


    }








}
