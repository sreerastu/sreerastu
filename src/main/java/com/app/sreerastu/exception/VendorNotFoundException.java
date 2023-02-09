package com.app.sreerastu.exception;

public class VendorNotFoundException extends Exception{

    public VendorNotFoundException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public VendorNotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public VendorNotFoundException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public VendorNotFoundException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}

