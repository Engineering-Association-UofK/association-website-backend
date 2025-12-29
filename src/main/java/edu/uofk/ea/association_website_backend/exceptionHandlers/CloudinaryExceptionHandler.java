package edu.uofk.ea.association_website_backend.exceptionHandlers;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CloudinaryExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(Exception e) {

        BaseErrorResponse error = new BaseErrorResponse(
                0,
                e.getMessage(),
                Instant.now().getEpochSecond()
        );

        if (e instanceof UnauthorizedException)
            error.setStatus(HttpStatus.UNAUTHORIZED.value());
        else if (e instanceof IllegalArgumentException)
            error.setStatus(HttpStatus.BAD_REQUEST.value());
        else
            error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }
}
