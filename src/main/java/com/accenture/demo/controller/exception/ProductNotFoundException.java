package com.accenture.demo.controller.exception;

import java.text.MessageFormat;

public class ProductNotFoundException extends Exception {
    private Long id;

    public static ProductNotFoundException createWith(Long id) {
        return new ProductNotFoundException(id);
    }

    private ProductNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Product with id {0} not found.", id);
    }
}
