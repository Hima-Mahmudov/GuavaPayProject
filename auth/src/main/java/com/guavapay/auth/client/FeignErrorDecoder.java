package com.guavapay.auth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.auth.exception.FeignClientException;
import com.guavapay.auth.exception.response.Error;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Exception decode(String resource, Response response) {

        Error errorResponse = mapper.readValue(response.body().asInputStream(), Error.class);

        switch (response.status()) {
            case 400: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.BAD_REQUEST);
            }
            case 401: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.UNAUTHORIZED);
            }
            case 403: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.FORBIDDEN);
            }
            case 404: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.NOT_FOUND);
            }
            case 409: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.CONFLICT);
            }
            case 500: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default: {
                return new FeignClientException(errorResponse.getMessage(), errorResponse.getStatus(), resource, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}