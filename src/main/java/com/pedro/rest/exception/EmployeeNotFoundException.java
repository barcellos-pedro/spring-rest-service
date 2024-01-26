package com.pedro.rest.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "Could not found employee with id: %s";

    public EmployeeNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
