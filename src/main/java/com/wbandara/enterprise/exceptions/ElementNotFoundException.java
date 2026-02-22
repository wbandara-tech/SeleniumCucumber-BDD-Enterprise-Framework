package com.wbandara.enterprise.exceptions;

/**
 * Thrown when an element cannot be found or interacted with.
 */
public class ElementNotFoundException extends FrameworkException {

    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

