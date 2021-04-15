package com.Kiiko.ExhibitionsApp.exceptions;


import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;

public class UserAlreadyExistException extends ServiceException {
    private static final String DEFAULT_MESSAGE = "User already exists ";

    public UserAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistException(String email) {
        super(DEFAULT_MESSAGE + email);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR;
    }
}
