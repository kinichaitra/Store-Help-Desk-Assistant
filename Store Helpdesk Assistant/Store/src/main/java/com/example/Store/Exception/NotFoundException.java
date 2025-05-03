package com.example.Store.Exception;


/**
 * NotFoundException when details are incorrect
 */

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2142151L;

    public NotFoundException(String msg) {
        super(msg);
    }
}