package com.opendebate.messenger.persistence;

import java.util.List;

public interface PersistentStore<T> {
    public T get(Integer id);
    public List<T> getAll();
    public T create(T t);
    public T update(Integer id, T update);
    public void delete(Integer id);
}
