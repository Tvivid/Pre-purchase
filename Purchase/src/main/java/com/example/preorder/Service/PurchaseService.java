package com.example.preorder.Service;


import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Entity.*;
import com.example.preorder.Entity.type.PurchaseStatus;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Feign.ProductClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                                                .status(PurchaseStatus.previous)
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

    //주문 리스트
    @Transactional
    public Page<Order> OrderList(String token, Pageable pageable){

        Long member = userFeignClient.getMember(token);

        Page<Order> orders = orderRepository.findByMemberId(member, pageable);
        return orders;
    }


    public void cancelOrder(Long orderId){

        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException());
        order.cancel();

        //재고량 복구
        Long productId=order.getProductId();
        Long quantity=order.getQuantity();
        productClient.addStock(productId, quantity);

    }


    public String payment(Long orderId){
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException());

        order.updateStatus(PurchaseStatus.processing);

        if(Math.random()>0.2){
            order.updateStatus(PurchaseStatus.completed);
            return "결제 완료";
        }
        order.updateStatus(PurchaseStatus.previous);
        order.cancel();

        //재고량 복구
        Long productId=order.getProductId();
        Long quantity=order.getQuantity();
        productClient.addStock(productId, quantity);

        return "잔액이 부족합니다";
        

    }








}
