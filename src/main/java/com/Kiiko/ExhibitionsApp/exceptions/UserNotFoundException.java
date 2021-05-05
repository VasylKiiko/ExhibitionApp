package com.Kiiko.ExhibitionsApp.exceptions;


import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;

public class UserNotFoundException extends ServiceException {
    private static final String DEFAULT_MASSAGE = "User not found ";

    public UserNotFoundException() {
        super(DEFAULT_MASSAGE);
    }

    public UserNotFoundException(String message) {
        super(DEFAULT_MASSAGE + message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR;
    }
}
