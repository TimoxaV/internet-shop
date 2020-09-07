package com.internet.shop.service.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Order;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.OrderService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getUserId());
        order.setProducts(shoppingCart.getProducts());
        orderDao.create(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getAllOrders().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long id) {
        return orderDao.getById(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAllOrders();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.deleteById(id);
    }
}
