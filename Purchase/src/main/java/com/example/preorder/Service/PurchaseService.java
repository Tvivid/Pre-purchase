package com.example.preorder.Service;


import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Entity.*;
import com.example.preorder.Entity.type.PurchaseStatus;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Feign.ProductClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final OrderRepository orderRepository;
    private final UserFeignClient userFeignClient;
    private final ProductClient productClient;



    //주문 생성
    public void create(String token, OrderDTO orderDTO){

        Long memberId = userFeignClient.getMember(token);

        stockValidation(orderDTO);


        Orders orders = Orders.builder()
                        .memberId(memberId)
                                .productId(orderDTO.getProductId())
                                        .quantity(orderDTO.getQuantity())
                                                .status(PurchaseStatus.previous)
                                                        .build();
        orderRepository.save(orders);


    }

    //재고량 유효성 검사
    public void stockValidation(OrderDTO orderDTO){
        Long stock= productClient.checkStock(orderDTO.getProductId());

        if(stock< orderDTO.getQuantity()){
            throw new CustomException();
        }
        //재고량 감소
        productClient.subStock(orderDTO);
    }

    //주문 리스트
    @Transactional
    public Page<Orders> OrderList(String token, Pageable pageable){

        Long member = userFeignClient.getMember(token);

        Page<Orders> orders = orderRepository.findByMemberId(member, pageable);
        return orders;
    }


    public void cancelOrder(Long orderId){

        Orders orders =orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException());
        orders.cancel();

        OrderDTO orderDTO=OrderDTO.entityToOrderDTO(orders);

        //재고량 복구
        productClient.addStock(orderDTO);

    }


    public String payment(Long orderId){
        Orders orders =orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException());

        orders.updateStatus(PurchaseStatus.processing);

        if(Math.random()>0.2){
            orders.updateStatus(PurchaseStatus.completed);
            return "결제 완료";
        }
        orders.updateStatus(PurchaseStatus.previous);
        orders.cancel();

        OrderDTO orderDTO=OrderDTO.entityToOrderDTO(orders);

        //재고량 복구
        productClient.addStock(orderDTO);

        return "잔액이 부족합니다";
        

    }








}
