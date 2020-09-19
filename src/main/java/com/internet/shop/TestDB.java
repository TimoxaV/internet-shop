package com.internet.shop;

import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.model.Product;
import java.math.BigDecimal;
import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        Product xiaomi = new Product("xiaomi", new BigDecimal(1000));

        System.out.println("Create");
        ProductDaoJdbcImpl productDao = new ProductDaoJdbcImpl();
        productDao.create(xiaomi);
        System.out.println(xiaomi.toString());

        System.out.println("Get");
        productDao.get(xiaomi.getId()).ifPresent(p -> System.out.println(p.toString()));

        System.out.println("Update");
        xiaomi.setPrice(new BigDecimal(1200));
        productDao.update(xiaomi);
        productDao.get(xiaomi.getId()).ifPresent(p -> System.out.println(p.toString()));

        System.out.println("Get all");
        Product samsung = new Product("samsung", new BigDecimal(1200));
        productDao.create(samsung);
        List<Product> all = productDao.getAll();
        for (Product prod : all) {
            System.out.println(prod.toString());
        }

        System.out.println("Delete");
        productDao.deleteById(samsung.getId());
        all = productDao.getAll();
        for (Product prod : all) {
            System.out.println(prod.toString());
        }
    }
}
