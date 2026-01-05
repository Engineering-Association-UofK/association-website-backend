package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class VerificationCodeException extends RuntimeException{

    public VerificationCodeException(String message) {
        super(message);
    }
}
