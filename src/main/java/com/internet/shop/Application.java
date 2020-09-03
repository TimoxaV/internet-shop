package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.math.BigDecimal;

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
        for (Product product : productService.getAllProducts()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        productService.delete(samsung);
        for (Product product : productService.getAllProducts()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        apple.setPrice(new BigDecimal(2500));
        productService.update(apple);
        for (Product product : productService.getAllProducts()) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------------\n");
        productService.deleteById(appleX.getId());
        for (Product product : productService.getAllProducts()) {
            System.out.println(product.toString());
        }
    }
}
