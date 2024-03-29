package com.example.preorder.Service;

import com.example.preorder.Dto.OrderDTO;
import com.example.preorder.Dto.ProductDTO;
import com.example.preorder.Dto.StockDTO;
import com.example.preorder.Entity.Product;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Feign.StockFeignClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final UserFeignClient userFeignClient;

    private final StockFeignClient stockFeignClient;



    //상품 등록
    @Transactional
    public void save(String token, ProductDTO productDTO){

        Long member = userFeignClient.getMember(token);

        String title= productDTO.getTitle();
        String content= productDTO.getContent();
        Long price= productDTO.getPrice();
        Long stock = productDTO.getStock();
        LocalDateTime reservation = productDTO.getReservation();
        boolean reserved = productDTO.isReserved();
        Product product = Product.builder()
                .title(title)
                .content(content)
                .price(price)
                .stock(stock)
                .reserved(reserved)
                .reservation(reservation)
                .build();

        productRepository.save(product);

        Long productId=product.getId();
        //문제가 있으면 id를 가져오는 부분에서 문제
        StockDTO stockDTO = StockDTO.builder()
                .productId(productId)
                .quantity(stock)
                .build();
        stockFeignClient.saveStock(stockDTO);
    }

    @Transactional
    public void update(String token, Long productId, ProductDTO productDTO){

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        Long member = userFeignClient.getMember(token);

        if(product.getMemberId()!=member){
            throw new CustomException();
        }

        product.update(productDTO.getTitle(), productDTO.getContent(), productDTO.getPrice());


    }

    @Transactional
    public void subStock(OrderDTO orderDTO){
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(()->new CustomException());

        product.subStock(orderDTO.getQuantity());
    }

    @Transactional
    public void addStock(OrderDTO orderDTO){
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(()->new CustomException());

        product.addStock(orderDTO.getQuantity());
    }

    @Transactional
    public Page<Product> productList(String token, Pageable pageable){
        Long member = userFeignClient.getMember(token);

        return productRepository.findAll(pageable);
    };

    @Transactional
    public Product productInfo(String token,Long productId){
        Long member = userFeignClient.getMember(token);
        Product product= productRepository.findById(productId)
                .orElseThrow(()->new CustomException());
        return product;
    }

    @Transactional
    public Long checkStock(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        Long stock = product.getStock();

        return stock;
    }





}
