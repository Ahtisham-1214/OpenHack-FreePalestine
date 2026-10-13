package com.example.openhackFreePalestine.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String className;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String className) {
        super(message);
        this.className = className;
    }

}
