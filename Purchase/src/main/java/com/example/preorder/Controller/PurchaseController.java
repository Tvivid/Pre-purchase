package com.example.preorder.Controller;


import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Dto.PaymentDTO;
import com.example.preorder.Entity.Orders;
import com.example.preorder.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    //주문 정보 리스트 출력
    @GetMapping
    public ResponseEntity<Page<Orders>> NewsFeed(@RequestHeader("Authorization") String accessToken){
        accessToken=accessToken.substring(7);

        int page = 0; // 첫 번째 페이지
        int size = 10; // 페이지 당 항목 수
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Orders> orders = purchaseService.OrderList(accessToken,pageable);
        return ResponseEntity.ok(orders);
    }

    //주문 생성
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestHeader("Authorization") String accessToken,@RequestBody OrderDTO orderDTO){
        accessToken=accessToken.substring(7);
        purchaseService.create(accessToken, orderDTO);
        return ResponseEntity.ok("주문이 생성되었습니다.");
    }

    //주문 취소
    @DeleteMapping
    public ResponseEntity<String>  cancelOrder(@RequestBody PaymentDTO paymentDTO){
        purchaseService.cancelOrder(paymentDTO.getOrderId());
        return ResponseEntity.ok("주문을 취소하였습니다.");
    }

    //결제
    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody PaymentDTO paymentDTO){
        String result=purchaseService.payment(paymentDTO.getOrderId());
        return ResponseEntity.ok(result);
    }
}
