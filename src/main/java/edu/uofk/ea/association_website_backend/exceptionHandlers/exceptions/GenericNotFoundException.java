package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class GenericNotFoundException extends RuntimeException{

    public GenericNotFoundException(String message) {
        super(message);
    }
}
