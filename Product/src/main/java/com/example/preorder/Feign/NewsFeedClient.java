package com.example.preorder.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "newsFeed", url = "http://localhost:8082/v1/internal")
public interface NewsFeedClient {

    @PostMapping("/create")
    void createActivity(Activity activity);


}
