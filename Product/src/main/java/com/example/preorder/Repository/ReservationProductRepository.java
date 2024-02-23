package com.example.preorder.Repository;

import com.example.preorder.Entity.ReservationProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductRepository extends JpaRepository<ReservationProduct,Long> {
}
