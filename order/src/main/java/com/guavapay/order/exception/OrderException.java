package com.guavapay.order.exception;

public class OrderException extends RuntimeException {
    public OrderException(String s) {
        super(s);
    }

    public OrderException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
