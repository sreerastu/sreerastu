package com.app.sreerastu.exception;

public class VendorNotAvailableException extends Exception{

    public VendorNotAvailableException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public VendorNotAvailableException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public VendorNotAvailableException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public VendorNotAvailableException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
