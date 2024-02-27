package com.example.preorder.Controller;

import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/internal/product")
@RequiredArgsConstructor
public class InternalProductController {

    @Autowired
    private ProductService productService;



    @GetMapping
    Long checkStock(@RequestBody Long productId){
        Long stock = productService.checkStock(productId);
        return stock;
    };

    @PostMapping("/subStock")
    void subStock(@RequestBody OrderDTO orderDTO){
        productService.subStock(orderDTO);

    };

    @PostMapping("/addStock")
    void addStock(@RequestBody OrderDTO orderDTO){
        productService.addStock(orderDTO);

    };
}
