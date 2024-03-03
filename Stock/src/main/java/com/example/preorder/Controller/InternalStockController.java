package com.example.preorder.Controller;

import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Dto.StockDTO;
import com.example.preorder.Service.ProductService;
import com.example.preorder.Service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/internal/stock")
@RequiredArgsConstructor
public class InternalStockController {

    @Autowired
    private StockService stockService;



    @GetMapping
    Long checkStock(@RequestParam Long productId){
        Long stock = stockService.checkStock(productId);
        return stock;
    };

    @PostMapping
    void saveStock(@RequestBody StockDTO stockDTO){
        stockService.save(stockDTO);
    }

    @PostMapping("/subStock")
    void subStock(@RequestBody OrderDTO orderDTO){
        stockService.subStock(orderDTO);

    };

    @PostMapping("/addStock")
    void addStock(@RequestBody OrderDTO orderDTO){
        stockService.addStock(orderDTO);

    };


}
