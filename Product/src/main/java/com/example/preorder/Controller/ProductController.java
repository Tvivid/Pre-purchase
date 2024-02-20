package com.example.preorder.Controller;

import com.example.preorder.Dto.ProductDTO;
import com.example.preorder.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> ProductSave(@RequestHeader("Authorization") String accesstoken, @RequestBody ProductDTO productDTO){
        accesstoken=accesstoken.substring(7);
        productService.save(accesstoken, productDTO);

        return ResponseEntity.ok("상품이 업로드 되었습니다.");

    }


}
