package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
