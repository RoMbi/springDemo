package com.accenture.demo.repository;

import com.accenture.demo.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    Product findByName(String name);

    @Query("select p from Product p")
    Page<Product> findAllPage(Pageable pageable);

    @Query("select p from Product p where p.price between ?1 and ?2")
    Page<Product> findByPriceRange(Pageable pageable, BigDecimal min, BigDecimal max);
}
