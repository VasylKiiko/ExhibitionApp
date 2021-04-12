package com.Kiiko.ExhibitionsApp.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User not found with " + message);
    }
}
