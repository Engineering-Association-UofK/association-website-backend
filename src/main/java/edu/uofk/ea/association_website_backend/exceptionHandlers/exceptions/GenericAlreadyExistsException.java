package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class GenericAlreadyExistsException extends RuntimeException{

    public GenericAlreadyExistsException(String message) {
        super(message);
    }
}
