package com.app.sreerastu.exception;

public class DuplicateVendorException extends Exception{
    public DuplicateVendorException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public DuplicateVendorException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public DuplicateVendorException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public DuplicateVendorException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}

