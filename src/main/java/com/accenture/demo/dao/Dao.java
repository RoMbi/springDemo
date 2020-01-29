package com.accenture.demo.dao;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(Long id);

    List<T> getAll();

    Optional<T> save(@NonNull T t);

    Boolean update(@NonNull T t);

    Boolean delete(@NonNull Long id);
}
