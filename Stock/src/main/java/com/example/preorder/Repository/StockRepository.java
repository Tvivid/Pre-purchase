package com.example.preorder.Repository;

import com.example.preorder.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock,Long> {
    Optional<Stock> findByProductId(Long productId);
}
