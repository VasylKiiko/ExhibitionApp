package com.Kiiko.ExhibitionsApp.exceptions;

import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;

public class NoAccessToRecourseException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Access forbidden";

    public NoAccessToRecourseException() {
        super(DEFAULT_MESSAGE);
    }

    public NoAccessToRecourseException(String message) {
        super(message);
    }

    public ErrorType getErrorType() {
        return ErrorType.ACCESS_ERROR;
    }
}
