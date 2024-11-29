package com.example.spring_data_jdbc.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();

    void update(T entity);
    void deleteById(ID id);
    void delete(T entity);
}
