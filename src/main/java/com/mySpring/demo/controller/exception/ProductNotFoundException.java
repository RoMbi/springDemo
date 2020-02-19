package com.mySpring.demo.controller.exception;

import java.text.MessageFormat;
import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {
    private String field;
    private String value;

    public static ProductNotFoundException createWith(String field, String value) {
        return new ProductNotFoundException(field, value);
    }

    private ProductNotFoundException(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Product with {0}: {1} not found.", field, value);
    }
}
