package com.internet.shop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T item);

    Optional<T> getById(K id);

    T update(T item);

    boolean deleteById(K id);

    List<T> getAll();
}
