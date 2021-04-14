package com.Kiiko.ExhibitionsApp.exceptions;

public class ExhibitionNotFoundException extends RuntimeException{
    public ExhibitionNotFoundException(String message) {
        super(message);
    }
}
