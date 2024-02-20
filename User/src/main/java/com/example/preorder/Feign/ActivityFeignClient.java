package com.example.preorder.Feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Activity", url = "http://localhost:8081/v1")
public interface ActivityFeignClient {
}
