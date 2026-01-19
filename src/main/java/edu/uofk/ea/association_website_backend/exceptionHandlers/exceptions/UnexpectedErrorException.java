package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class UnexpectedErrorException extends RuntimeException{

    public UnexpectedErrorException(String message) {
        super(message);
    }
}
