package com.Kiiko.ExhibitionsApp.exceptions;

import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;

public class ExhibitionNotFoundException extends ServiceException{
    private static final String DEFAULT_MESSAGE = "Exhibition not found!";

    public ExhibitionNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ExhibitionNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR;
    }
}
