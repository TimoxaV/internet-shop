package com.internet.shop.dao;

import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    Optional<Order> getById(Long orderId);

    Order update(Order order);

    boolean deleteById(Long orderId);

    List<Order> getAllOrders();
}
