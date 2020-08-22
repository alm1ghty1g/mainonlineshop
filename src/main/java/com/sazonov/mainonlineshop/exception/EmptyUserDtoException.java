package com.sazonov.mainonlineshop.exception;

public class EmptyUserDtoException extends RuntimeException {
    public EmptyUserDtoException() {
        super();
    }

    public EmptyUserDtoException(String message) {
        super(message);
    }

    public EmptyUserDtoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyUserDtoException(Throwable cause) {
        super(cause);
    }
}
