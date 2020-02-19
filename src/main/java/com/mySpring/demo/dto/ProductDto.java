package com.mySpring.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Component
@Getter
@Setter
public class ProductDto implements Serializable {
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;
}
