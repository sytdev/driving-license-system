package org.licesys.license.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    void save(T t);
    T update(ID id, T t);
    void delete(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
}
