package com.opendebate.messenger.persistence;

import java.util.List;

public interface PersistentStore<T> {
    T get(Integer id);
    List<T> getAll();
    T create(T t);
    T update(Integer id, T update);
    void delete(Integer id);
}
