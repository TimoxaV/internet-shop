package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.math.BigDecimal;
import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("com.internet.shop");
        ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);

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
    }
}
