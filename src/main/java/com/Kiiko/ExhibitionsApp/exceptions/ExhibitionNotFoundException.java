package com.Kiiko.ExhibitionsApp.exceptions;

import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;

public class ExhibitionNotFoundException extends ServiceException{
    public ExhibitionNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR;
    }
}
