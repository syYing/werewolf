package com.lucien.werewolf;

import org.springframework.http.HttpStatus;

public class HttpException extends Exception {

    private HttpStatus status;
    private String message;

    public HttpException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
