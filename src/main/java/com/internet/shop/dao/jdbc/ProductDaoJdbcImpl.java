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
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            while (generatedKey.next()) {
                product.setId(generatedKey.getLong(1));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create product " + product.getName()
                    + " with price " + product.getPrice(), e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT product_id, name, price FROM products "
                + "WHERE product_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the product with id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE product_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
        return product;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE products SET deleted = TRUE WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete product with id " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT product_id, name, price FROM products WHERE deleted = FALSE";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the product", e);
        }
        return products;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");
        Product product = new Product(name, price);
        product.setId(productId);
        return product;
    }
}
