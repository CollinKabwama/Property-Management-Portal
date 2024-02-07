package com.propertymanagement.portal.exception;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7572787248653397517L;

    public RecordNotFoundException(Exception e) {
        super(e);
    }

    public RecordNotFoundException(final String message) {
        super(message);
    }
}
