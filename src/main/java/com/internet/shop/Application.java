package com.internet.shop;

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
import java.util.Set;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product xiaomi = new Product("xiaomi", new BigDecimal(1000));
        Product samsung = new Product("samsung", new BigDecimal(1200));
        Product apple = new Product("apple", new BigDecimal(3000));
        Product appleX = new Product("appleX", new BigDecimal(4000));

        productService.create(xiaomi);
        productService.create(samsung);
        productService.create(apple);
        productService.create(appleX);
        for (Product product : productService.getAll()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        productService.delete(samsung.getId());
        for (Product product : productService.getAll()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        apple.setPrice(new BigDecimal(2500));
        productService.update(apple);
        for (Product product : productService.getAll()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        productService.delete(appleX.getId());
        for (Product product : productService.getAll()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        System.out.println("Users");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user1 = new User("Bob", "bob", "111", Set.of(Role.of("USER")));
        User user2 = new User("John", "john", "222", Set.of(Role.of("USER")));
        User user3 = new User("Matt", "matt", "333", Set.of(Role.of("USER")));
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        for (User user : userService.getAll()) {
            System.out.println(user.toString());
        }
        System.out.println("------------------------------------\n");
        userService.delete(3L);
        for (User user : userService.getAll()) {
            System.out.println(user.toString());
        }
        System.out.println("------------------------------------\n");
        user1.setPassword("444");
        userService.update(user1);
        for (User user : userService.getAll()) {
            System.out.println(user.toString());
        }
        System.out.println("------------------------------------\n");
        System.out.println("Carts");
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart1 = new ShoppingCart(1L);
        ShoppingCart shoppingCart2 = new ShoppingCart(2L);
        shoppingCartService.addProduct(shoppingCart1, xiaomi);
        shoppingCartService.addProduct(shoppingCart1, apple);
        shoppingCartService.addProduct(shoppingCart2, apple);
        shoppingCartService.create(shoppingCart1);
        shoppingCartService.create(shoppingCart2);
        System.out.println(shoppingCartService.getByUserId(1L));
        System.out.println("------------------------------------\n");
        shoppingCartService.deleteProduct(shoppingCart1, apple);
        System.out.println(shoppingCartService.getByUserId(1L));
        System.out.println("------------------------------------\n");

        System.out.println("Orders");
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCart1);
        orderService.completeOrder(shoppingCart2);
        for (Order order : orderService.getAll()) {
            System.out.println(order.toString());
        }
        System.out.println("------------------------------------\n");
        System.out.println(orderService.getUserOrders(1L));
    }
}
