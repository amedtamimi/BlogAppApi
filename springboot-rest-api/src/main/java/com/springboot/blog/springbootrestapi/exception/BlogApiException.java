package com.springboot.blog.springbootrestapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class BlogApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public BlogApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
