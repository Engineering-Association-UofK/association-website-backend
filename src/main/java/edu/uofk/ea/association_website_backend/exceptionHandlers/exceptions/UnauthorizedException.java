package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message) {
        super(message);
    }
}
