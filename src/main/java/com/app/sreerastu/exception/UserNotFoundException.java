package com.app.sreerastu.exception;

public class UserNotFoundException extends  Exception {
    public UserNotFoundException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public UserNotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}




