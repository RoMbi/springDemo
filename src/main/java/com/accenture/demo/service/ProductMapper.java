package com.accenture.demo.service;

import com.accenture.demo.dto.ProductDto;
import com.accenture.demo.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    static ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        ProductMapper.modelMapper = modelMapper;
    }

    public static ProductDto map(@NonNull Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public static List<ProductDto> map(List<Product> products) {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(map(product));
        }
        return dtos;
    }

    public static Product convertToEntity(@NonNull ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

}
