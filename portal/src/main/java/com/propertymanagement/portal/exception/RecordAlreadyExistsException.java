package com.propertymanagement.portal.exception;


import java.io.Serial;

public class RecordAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7572787248653397517L;

    public RecordAlreadyExistsException(Exception e) {
        super(e);
    }

    public RecordAlreadyExistsException(final String message) {
        super(message);
    }
}
