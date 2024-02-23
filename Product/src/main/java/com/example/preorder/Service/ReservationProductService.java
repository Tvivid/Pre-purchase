package com.example.preorder.Service;

import com.example.preorder.Dto.ProductDTO;
import com.example.preorder.Dto.ReservationProductDTO;
import com.example.preorder.Entity.Product;
import com.example.preorder.Entity.ReservationProduct;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.ProductRepository;
import com.example.preorder.Repository.ReservationProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ReservationProductService {


    private final ReservationProductRepository reservationProductRepository;

    private final UserFeignClient userFeignClient;



    //상품 등록
    @Transactional
    public void save(String token, ReservationProductDTO reservationProductDTO){

        Long member = userFeignClient.getMember(token);

        String title= reservationProductDTO.getTitle();
        String content= reservationProductDTO.getContent();
        Long price= reservationProductDTO.getPrice();
        Long stock = reservationProductDTO.getStock();
        LocalDateTime reservation = reservationProductDTO.getReservation();
        ReservationProduct reservationProduct = ReservationProduct.builder()
                .title(title)
                .content(content)
                .price(price)
                .stock(stock)
                .reservation(reservation)
                .build();

        reservationProductRepository.save(reservationProduct);

    }

    @Transactional
    public void update(String token, Long productId, ReservationProductDTO reservationProductDTO){

        ReservationProduct reservationProduct = reservationProductRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        Long member = userFeignClient.getMember(token);

        if(reservationProduct.getMemberId()!=member){
            throw new CustomException();
        }

        reservationProduct.update(reservationProductDTO.getTitle(), reservationProductDTO.getContent(),
                reservationProductDTO.getPrice(),reservationProductDTO.getReservation());


    }

    @Transactional
    public void subStock(Long productId, Long quantity){
        ReservationProduct reservationProduct = reservationProductRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        reservationProduct.subStock(quantity);
    }

    @Transactional
    public void addStock(Long productId, Long quantity){
        ReservationProduct reservationProduct= reservationProductRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        reservationProduct.addStock(quantity);
    }
    //상품 리스트
    @Transactional
    public Page<ReservationProduct> productList(Pageable pageable){
        return reservationProductRepository.findAll(pageable);
    };
    //상품 상세 정보
    @Transactional
    public ReservationProduct productInfo(Long productId){
        ReservationProduct reservationProduct=reservationProductRepository.findById(productId)
                .orElseThrow(()->new CustomException());
        return reservationProduct;
    }
    //재고 확인
    @Transactional
    public Long checkStock(Long productId){
        ReservationProduct reservationProduct=reservationProductRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        Long stock = reservationProduct.getStock();

        return stock;
    }



}
