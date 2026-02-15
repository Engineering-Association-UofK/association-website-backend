package edu.uofk.ea.association_website_backend.exceptionHandlers;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.VerificationCodeException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.JwtSignatureException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UserAlreadyExistsException;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(JwtSignatureException.class)
    public ResponseEntity<@NonNull BaseErrorResponse> handleException(JwtSignatureException e) {

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid token.",
                Instant.now().getEpochSecond()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<@NonNull BaseErrorResponse> handleException(NoResourceFoundException ex) {
        return new ResponseEntity<>(
                new BaseErrorResponse(
                        HttpStatus.NOT_FOUND.value(), 
                        "The requested endpoint was not found.", 
                        System.currentTimeMillis()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<@NonNull BaseErrorResponse> handleException(BadCredentialsException ex) {
        return new ResponseEntity<>(
                new BaseErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Invalid credentials.",
                        System.currentTimeMillis()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<@NonNull BaseErrorResponse> handleException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new BaseErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        System.currentTimeMillis()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(VerificationCodeException.class)
    public ResponseEntity<@NonNull BaseErrorResponse> handleException(VerificationCodeException ex) {
        return new ResponseEntity<>(
                new BaseErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage(),
                        System.currentTimeMillis()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }
}
