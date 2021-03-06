package com.guavapay.order.repository;

import com.guavapay.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByUser_Id(Long userId);
}
