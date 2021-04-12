package com.Kiiko.ExhibitionsApp.exceptions;


public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String email) {
        super("user with email " + email + " already exists");
    }
}
