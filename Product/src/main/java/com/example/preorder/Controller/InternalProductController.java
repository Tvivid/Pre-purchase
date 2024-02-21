package com.example.preorder.Controller;

import com.example.preorder.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/internal/product")
@RequiredArgsConstructor
public class InternalProductController {

    @Autowired
    private ProductService productService;



    @GetMapping
    Long checkStock(Long productId){
        Long stock = productService.checkStock(productId);
        return stock;
    };

    @PostMapping("/subStock")
    void subStock(Long productId, Long quantity){
        productService.subStock(productId, quantity);

    };

    @PostMapping("/addStock")
    void addStock(Long productId, Long quantity){
        productService.addStock(productId, quantity);

    };
}
