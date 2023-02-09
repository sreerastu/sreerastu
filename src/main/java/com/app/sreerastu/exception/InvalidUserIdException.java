package com.app.sreerastu.exception;

public class InvalidUserIdException extends  Exception {

    public InvalidUserIdException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public InvalidUserIdException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public InvalidUserIdException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public InvalidUserIdException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}

