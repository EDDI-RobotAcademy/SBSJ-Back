package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    List<OrderInfo> findAllByMember_MemberIdOrderByOrderDateDesc(Long memberId);

    @Query("select oi.orderNo from OrderInfo oi")
    List<String> findFullOrderNumberByOrderNumber();

    @Query("select oi from OrderInfo oi join fetch oi.orderItemList oiList join fetch oiList.product p where oi.orderId = :orderId")
    OrderInfo findOrderInfoWithOrderItemListAndProductByOrderId(Long orderId);

}
