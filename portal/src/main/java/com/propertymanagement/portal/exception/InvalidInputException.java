package com.propertymanagement.portal.exception;

import java.io.Serial;

public class InvalidInputException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7572787248653397517L;

    public InvalidInputException(Exception e) {
        super(e);
    }

    public InvalidInputException(final String message) {
        super(message);
    }
}
