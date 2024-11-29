package com.example.spring_data_jdbc.model.repository;

import java.util.Optional;

public interface CrudRepository<T, ID> {

    <S extends T> S save(S entity);
    Optional<T> findById(ID id);
    Iterable<T> findAll();
    void deleteById(ID id);
}
