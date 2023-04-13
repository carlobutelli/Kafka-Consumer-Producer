package com.kqueue.consumer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException{

    private final HttpStatus status;
    private final String error;

    public CustomException(HttpStatus status, String error) {
        super(error);
        this.status = status;
        this.error = error;
    }

    public CustomException(HttpStatus status, String error, Throwable e) {
        super(error, e);
        this.status = status;
        this.error = error;
    }
}
