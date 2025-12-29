package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class WrongRequestBodyException extends RuntimeException{

    public WrongRequestBodyException(String message) {
        super(message);
    }
}
