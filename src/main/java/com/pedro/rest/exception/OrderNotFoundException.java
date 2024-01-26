package com.pedro.rest.exception;

public class OrderNotFoundException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "Could not found order with id: %s";

    public OrderNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
