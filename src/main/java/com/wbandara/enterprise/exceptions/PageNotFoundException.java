package com.wbandara.enterprise.exceptions;

/**
 * Thrown when a page fails to load or is not found.
 */
public class PageNotFoundException extends FrameworkException {

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

