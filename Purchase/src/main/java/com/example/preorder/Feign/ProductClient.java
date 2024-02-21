package com.example.preorder.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@FeignClient(name = "product", url = "http://localhost:8081/v1/internal/product")
public interface ProductClient {


    @GetMapping
    Long checkStock(Long productId);

    @PostMapping
    void subStock(Long productId, Long quantity);
}
