package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
