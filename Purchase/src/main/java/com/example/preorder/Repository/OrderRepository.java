package com.example.preorder.Repository;

import com.example.preorder.Entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    Page<Orders> findByMemberId(Long memberId, Pageable pageable);
}
