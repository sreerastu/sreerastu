package com.app.sreerastu.exception;

public class DuplicateUserException extends  Exception{

    public DuplicateUserException() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public DuplicateUserException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     * @param cause
     */
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cause
     */
    public DuplicateUserException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}

