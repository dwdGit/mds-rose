package com.dg.mdsrose.user.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super(String.format("User already exists with username: %s", username));
    }
}
