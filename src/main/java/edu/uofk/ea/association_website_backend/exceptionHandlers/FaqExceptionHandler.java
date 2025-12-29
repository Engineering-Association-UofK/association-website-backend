package edu.uofk.ea.association_website_backend.exceptionHandlers;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class FaqExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(GenericNotFoundException e) {

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now().getEpochSecond()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
