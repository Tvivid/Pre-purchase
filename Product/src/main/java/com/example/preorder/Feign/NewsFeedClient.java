package com.example.preorder.Feign;

import com.example.preorder.Dto.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "newsFeed", url = "http://localhost:8082/v1/internal")
public interface NewsFeedClient {

    @PostMapping("/create")
    void createActivity(Activity activity);


}
