package com.internet.shop.dao;

import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> getById(Long userId);

    User update(User user);

    boolean deleteById(Long userId);

    List<User> getAllUsers();
}
