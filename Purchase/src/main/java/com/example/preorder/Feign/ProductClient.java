package com.example.preorder.Feign;

import com.example.preorder.Dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "product", url = "http://localhost:8081/v1/internal/product")
public interface ProductClient {


    @GetMapping
    Long checkStock(@RequestParam("productId") Long productId);

    @PostMapping("/subStock")
    void subStock(@RequestBody OrderDTO orderDTO);

    @PostMapping("/addStock")
    void addStock(@RequestBody OrderDTO orderDTO);
}
