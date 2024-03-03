package com.example.preorder.Service;

import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Dto.StockDTO;
import com.example.preorder.Entity.Product;
import com.example.preorder.Entity.Stock;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    //재고량 제공
    private final StockRepository stockRepository;

    @Transactional
    public void save(StockDTO stockDTO){
        Stock stock = Stock.toSaveEntity(stockDTO);
        stockRepository.save(stock);

    }

    @Transactional
    public Long checkStock(Long productId){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()->new CustomException());

        Long quantity = stock.getStock();

        return quantity;
    }

    //재고량 감소

    @Transactional
    public void subStock(OrderDTO orderDTO){
        Stock stock = stockRepository.findByProductId(orderDTO.getProductId())
                .orElseThrow(()->new CustomException());

        stock.subStock(orderDTO.getQuantity());
    }
    @Transactional
    public void subStock(OrderDTO orderDTO){
        Stock stock = stockRepository.findByProductId(orderDTO.getProductId())
                .orElseThrow(()->new CustomException());

        stock.addStock(orderDTO.getQuantity());
    }



}
