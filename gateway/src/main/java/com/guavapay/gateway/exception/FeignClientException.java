package com.guavapay.gateway.exception;

import org.springframework.http.HttpStatus;

public class FeignClientException extends RuntimeException {

    private final String title;
    private final String resource;
    private final HttpStatus httpStatus;

    public FeignClientException(String message, String title, String resource, HttpStatus httpStatus) {
        super(message);
        this.title = title;
        this.resource = resource;
        this.httpStatus = httpStatus;
    }

    public FeignClientException(String message, Throwable cause, String title, String resource, HttpStatus httpStatus) {
        super(message, cause);
        this.title = title;
        this.resource = resource;
        this.httpStatus = httpStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getResource() {
        return resource;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
