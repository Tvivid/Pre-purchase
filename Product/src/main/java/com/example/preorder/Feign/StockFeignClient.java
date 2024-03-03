package com.example.preorder.Feign;


import com.example.preorder.Dto.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "stock", url = "http://localhost:8084/v1/internal/stock")
public interface StockFeignClient {


    @PostMapping
    void saveStock(@RequestBody StockDTO stockDTO);
}


