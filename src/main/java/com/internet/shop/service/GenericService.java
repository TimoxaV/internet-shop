package com.internet.shop.service;

import java.util.List;

public interface GenericService<T, K> {
    T create(T item);

    T get(K id);

    T update(T item);

    boolean delete(K id);

    List<T> getAll();
}
