package com.example.distribution_sales_techfira.exception;


public class CustomException extends RuntimeException {


    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
