package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product item) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insert =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, item.getName());
            insert.setBigDecimal(2, item.getPrice());
            insert.executeUpdate();
            ResultSet generatedKey = insert.getGeneratedKeys();
            while (generatedKey.next()) {
                item.setId(generatedKey.getLong(1));
            }
            return item;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create product", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT product_id, name, price FROM products "
                + "WHERE product_id = ? AND deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(query);
            select.setLong(1, id);
            ResultSet productRow = select.executeQuery();
            if (productRow.next()) {
                Long productId = productRow.getLong("product_id");
                String name = productRow.getString("name");
                BigDecimal price = productRow.getBigDecimal("price");
                Product product = new Product(name, price);
                product.setId(productId);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the product", e);
        }
        return Optional.empty();
    }

    @Override
    public Product update(Product item) {
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE product_id = ? AND deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement update = connection.prepareStatement(query);
            update.setString(1, item.getName());
            update.setBigDecimal(2, item.getPrice());
            update.setLong(3, item.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE products SET deleted = 1 WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement delete = connection.prepareStatement(query);
            delete.setLong(1, id);
            int deletedRow = delete.executeUpdate();
            if (deletedRow == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
        return false;
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT product_id, name, price FROM products WHERE deleted = 0";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(query);
            ResultSet productRow = select.executeQuery();
            while (productRow.next()) {
                Long productId = productRow.getLong("product_id");
                String name = productRow.getString("name");
                BigDecimal price = productRow.getBigDecimal("price");
                Product product = new Product(name, price);
                product.setId(productId);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the product", e);
        }
        return products;
    }
}
