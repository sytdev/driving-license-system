package org.licesys.license.exception;

public class BusinessRuleException extends RuntimeException{

    private String message;
    private Throwable cause;

    public BusinessRuleException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message,cause);
        this.message = message;
    }
}
