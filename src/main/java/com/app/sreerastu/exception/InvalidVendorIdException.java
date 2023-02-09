package com.app.sreerastu.exception;

public class InvalidVendorIdException extends Exception {

    public InvalidVendorIdException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public InvalidVendorIdException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public InvalidVendorIdException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public InvalidVendorIdException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}

