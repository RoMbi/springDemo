package com.accenture.demo.dao;

import com.accenture.demo.controller.exception.ProductNotFoundException;
import com.accenture.demo.entity.Product;
import com.accenture.demo.repository.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDao implements Dao<Product> {
    @Autowired
    ProductRepository productRepository;

    @SneakyThrows
    @Override
    public Optional<Product> get(Long id) {
        Product product;
        try {
            return Optional.of(productRepository.findById(id).get());
        } catch (Exception e) {
            throw ProductNotFoundException.createWith(id);
        }
    }

    @Override
    public Optional<Product> save(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());

        if (existingProduct != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Product already exists", new EntityExistsException());
        }

        return Optional.of(productRepository.save(product));
    }

    @Override
    public Boolean update(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        if (existingProduct == null) {
            return false;
        }

        productRepository.save(product);

        return true;
    }

    @Override
    public List getAll() {
        return productRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Boolean delete(Long id) {
        Product existingProduct;
        try {
            existingProduct = productRepository.findById(id).get();
            productRepository.delete(existingProduct);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
