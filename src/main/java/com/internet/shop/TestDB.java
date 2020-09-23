package com.internet.shop;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.dao.jdbc.OrderDaoJdbcImpl;
import com.internet.shop.dao.jdbc.ShoppingCartDaoJdbcImpl;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TestDB {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private static UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private static ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private static OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    public static void main(String[] args) {

        System.out.println("Products");
        Product xiaomi = new Product("xiaomi", new BigDecimal(1000));

        System.out.println("Create");
        productService.create(xiaomi);
        System.out.println(xiaomi.toString());

        System.out.println("Get");
        System.out.println(productService.get(xiaomi.getId()));

        System.out.println("Update");
        xiaomi.setPrice(new BigDecimal(1200));
        productService.update(xiaomi);
        System.out.println(productService.get(xiaomi.getId()));

        System.out.println("Get all");
        Product samsung = new Product("samsung", new BigDecimal(1200));
        productService.create(samsung);
        List<Product> all = productService.getAll();
        for (Product prod : all) {
            System.out.println(prod.toString());
        }

        System.out.println("Delete");
        productService.delete(samsung.getId());
        all = productService.getAll();
        for (Product prod : all) {
            System.out.println(prod.toString());
        }
        productService.create(samsung);

        System.out.println("--------------------------------------------");
        System.out.println("Users");

        User user1 = new User("john", "john", "123", Set.of(Role.of("USER")));
        userService.create(user1);
        System.out.println(user1.toString());

        Optional<User> john = userService.findByLogin("john");
        System.out.println(john.get());

        john.get().setName("jonny");
        userService.update(john.get());
        System.out.println(userService.get(john.get().getId()));

        User user2 = new User("sam", "sam", "111");
        user2.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(user2);
        System.out.println(userService.getAll());
        userService.delete(user2.getId());
        System.out.println(userService.getAll());

        System.out.println("--------------------------------------------");
        System.out.println("Shopping Carts");
        List<Product> products = new ArrayList<>();
        products.add(xiaomi);
        products.add(samsung);
        ShoppingCart shoppingCart = new ShoppingCart(john.get().getId());
        shoppingCart.setProducts(products);
        shoppingCartService.create(shoppingCart);
        System.out.println(shoppingCartService.getByUserId(john.get().getId()));
        ShoppingCartDao shoppingCartDao = new ShoppingCartDaoJdbcImpl();
        System.out.println(shoppingCartDao.get(1L));

        System.out.println(shoppingCartDao.update(shoppingCartDao.get(1L).get()));
        System.out.println(shoppingCartDao.getAll());

        System.out.println("--------------------------------------------");
        System.out.println("Orders");
        Order order = new Order(john.get().getId());
        order.setProducts(products);
        orderService.create(order);
        System.out.println(orderService.getUserOrders(john.get().getId()));
        OrderDao orderDao = new OrderDaoJdbcImpl();
        System.out.println(orderDao.get(1L));
        System.out.println(orderDao.update(orderDao.get(1L).get()));
        System.out.println(orderDao.deleteById(1L));
        System.out.println(orderDao.getAll());
    }
}
