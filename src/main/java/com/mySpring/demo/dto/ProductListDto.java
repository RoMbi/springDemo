package com.mySpring.demo.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductListDto {
    private List<ProductDto> products;

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
