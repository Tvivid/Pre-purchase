package com.example.preorder.Feign;

import com.example.preorder.Entity.Follow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "activity", url = "http://localhost:8081/v1/internal")
public interface ActivityFeignClient {

    @GetMapping("/following")
    Set<Long> getFollowing(Long memberId);
}
