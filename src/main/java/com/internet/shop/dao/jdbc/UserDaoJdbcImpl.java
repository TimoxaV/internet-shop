package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        User user;
        String query = "SELECT id, name, login, password, salt FROM users WHERE deleted = FALSE "
                + "AND login = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with login " + login, e);
        }
        user.setRoles(getUsersRoles(user));
        return Optional.of(user);
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users(name, login, password, salt) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user " + user.toString(), e);
        }
        createUsersRole(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        User user;
        String query = "SELECT id, name, login, password, salt FROM users WHERE deleted = FALSE "
                + "AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user with id " + id, e);
        }
        user.setRoles(getUsersRoles(user));
        return Optional.of(user);
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name = ?, login = ?, password = ? "
                + "WHERE deleted = FALSE AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id " + user.getId(), e);
        }
        deleteUsersRole(user.getId());
        createUsersRole(user);
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE users SET deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete product with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, login, password, salt FROM users WHERE deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
        for (User user : users) {
            user.setRoles(getUsersRoles(user));
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        return new User(id, name, login, password, salt);
    }

    private boolean createUsersRole(User user) {
        String query = "INSERT INTO users_roles(user_id, role_id) VALUES (?, "
                + "(SELECT id FROM roles WHERE name = ?))";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            for (Role role : user.getRoles()) {
                statement.setString(2, role.getRoleName().toString());
                statement.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't write users_roles", e);
        }
    }

    private boolean deleteUsersRole(Long id) {
        String query = "UPDATE users_roles SET deleted = TRUE WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete users role", e);
        }
    }

    private Set<Role> getUsersRoles(User user) {
        String query = "SELECT r.id, r.name FROM users_roles ur "
                + "JOIN roles r on r.id = ur.role_id WHERE ur.deleted = FALSE "
                + "AND r.deleted = FALSE AND ur.user_id = ?";
        Set<Role> roles = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("name"));
                role.setId(resultSet.getLong("id"));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get roles for user_id " + user.getId(), e);
        }
        return roles;
    }
}
