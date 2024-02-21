package com.example.preorder.Service;


import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Entity.*;
import com.example.preorder.Entity.type.PurchaseStatus;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Feign.ProductClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.ActivityRepository;
import com.example.preorder.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private OrderRepository orderRepository;
    private UserFeignClient userFeignClient;
    private ProductClient productClient;



    //주문 생성
    public void create(String token, OrderDTO orderDTO){

        Long memberId = userFeignClient.getMember(token);

        stockValidation(orderDTO.getProductId(), orderDTO.getQuantity());


        Order order = Order.builder()
                        .memberId(memberId)
                                .productId(orderDTO.getProductId())
                                        .quantity(orderDTO.getQuantity())
                                                .status(PurchaseStatus.processing)
                                                        .build();
        orderRepository.save(order);


    }

    //재고량 유효성 검사
    public void stockValidation(Long productId, Long quantity){
        Long stock= productClient.checkStock(productId);

        if(stock<quantity){
            throw new CustomException();
        }
        //재고량 감소
        productClient.subStock(productId, quantity);
    }

    public void cancelOrder(Long orderId){

        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException());
        order.cancel();
        Long productId=order.getProductId();
        Long quantity=order.getQuantity();
        productClient.addStock(productId, quantity);

    }








}
