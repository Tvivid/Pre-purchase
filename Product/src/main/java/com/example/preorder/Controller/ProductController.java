package com.example.preorder.Controller;

import com.example.preorder.Dto.ProductDTO;
import com.example.preorder.Entity.Product;
import com.example.preorder.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestHeader("Authorization") String accesstoken, @RequestBody ProductDTO productDTO){
        accesstoken=accesstoken.substring(7);
        productService.save(accesstoken, productDTO);

        return ResponseEntity.ok("상품이 업로드 되었습니다.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProdcut(@RequestHeader("Authorization") String accesstoken,@PathVariable(name = "id") Long productId,
                                                @RequestBody ProductDTO productDTO){
        accesstoken=accesstoken.substring(7);
        productService.update(accesstoken,productId, productDTO);
        return ResponseEntity.ok("상품수정이 완료 되었습니다.");
    }

    //상품 리스트
    @GetMapping
    public ResponseEntity<Page<Product>> productList(@RequestHeader("Authorization") String accessToken){
        accessToken=accessToken.substring(7);

        int page = 0; // 첫 번째 페이지
        int size = 10; // 페이지 당 항목 수
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Product> products = productService.productList(accessToken,pageable);
        return ResponseEntity.ok(products);
    }
    //상품 상세 정보
    @GetMapping("/{id}")
    public ResponseEntity<Product> productInfo(@RequestHeader("Authorization") String accesstoken,@PathVariable(name = "id") Long productId){
        accesstoken=accesstoken.substring(7);
        Product product=productService.productInfo(accesstoken,productId);
        return ResponseEntity.ok(product);
    }




}
