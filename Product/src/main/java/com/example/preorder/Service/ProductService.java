package com.example.preorder.Service;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Dto.ProductDTO;
import com.example.preorder.Entity.Product;
import com.example.preorder.Exception.CustomException;
import com.example.preorder.Feign.NewsFeedClient;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final UserFeignClient userFeignClient;



    //상품 등록
    @Transactional
    public void save(String token, ProductDTO productDTO){

        Long member = userFeignClient.getMember(token);

        String title= productDTO.getTitle();
        String content= productDTO.getContent();
        Long price= productDTO.getPrice();
        Long stock = productDTO.getStock();
        Product product = Product.builder()
                        .title(title)
                                .content(content)
                                        .price(price)
                                                .stock(stock)
                                                        .build();

        productRepository.save(product);

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
    public void subStock(Long productId, Long quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        product.subStock(quantity);
    }

    @Transactional
    public void addStock(Long productId, Long quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new CustomException());

        product.addStock(quantity);
    }


    public Page<Product> productList(Pageable pageable){
        return productRepository.findAll(pageable);
    };





}
