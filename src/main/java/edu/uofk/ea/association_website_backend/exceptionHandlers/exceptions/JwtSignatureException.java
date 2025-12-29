package edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions;

public class JwtSignatureException extends RuntimeException{

    public JwtSignatureException(String message) {
        super(message);
    }
}
