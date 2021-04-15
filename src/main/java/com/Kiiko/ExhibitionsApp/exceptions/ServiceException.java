package com.Kiiko.ExhibitionsApp.exceptions;

import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ServiceException extends RuntimeException{
    private ErrorType errorType;

    public ServiceException(String message) {
        super(message);
    }

    public ErrorType getErrorType() {
        return ErrorType.FATAL_ERROR;
    }
}
