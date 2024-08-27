package com.swiggy.app.location.exception;

/**
 * @author : Samiullah Makandar
 * @purpose :
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
