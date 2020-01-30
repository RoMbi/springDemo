package com.accenture.demo.service;

import com.accenture.demo.controller.exception.ProductNotFoundException;
import com.accenture.demo.dao.Dao;
import com.accenture.demo.dto.ProductDto;
import com.accenture.demo.entity.Product;
import com.accenture.demo.repository.ProductRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ProductHandler implements Dao<Product> {
    @Autowired
    static ModelMapper modelMapper;

    @Autowired
    ProductRepository productRepository;

    public static ProductDto convertToDto(@NonNull Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());

        return dto;
    }

    public static List<ProductDto> convertToDto(List<Product> products) {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(convertToDto(product));
        }

        return dtos;
    }

    public static Product convertToEntity(@NonNull ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    @SneakyThrows
    @Override
    public Optional<Product> get(Long id) {
        try {
            return Optional.of(productRepository.findById(id).get());
        } catch (Exception e) {
            throw ProductNotFoundException.createWith("id", id.toString());
        }
    }

    @Override
    public Optional<Product> save(Product product) {
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Boolean update(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        if (existingProduct == null) {
            throw ProductNotFoundException.createWith("name", product.getName());
        }

        productRepository.save(product);

        return true;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Boolean delete(Long id) {
        Product existingProduct;
        try {
            existingProduct = productRepository.findById(id).get();
            productRepository.delete(existingProduct);
        } catch (NoSuchElementException e) {
            throw ProductNotFoundException.createWith("id", id.toString());
        }

        return true;
    }

    public Product findByName(String name) {
        try {
            return productRepository.findByName(name);
        } catch (NoSuchElementException e) {
            throw ProductNotFoundException.createWith("name", name);
        }
    }
}
