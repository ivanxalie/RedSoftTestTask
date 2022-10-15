package org.redsoft.test.accounting.entities;

public class EntityCustomException extends RuntimeException{
    public EntityCustomException() {
    }

    public EntityCustomException(String message) {
        super(message);
    }

    public EntityCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityCustomException(Throwable cause) {
        super(cause);
    }

    public EntityCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
