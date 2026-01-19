package edu.uofk.ea.association_website_backend.exceptionHandlers;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnexpectedErrorException;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class FaqExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(FaqExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(GenericNotFoundException e) {

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now().getEpochSecond()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(UnexpectedErrorException e) {

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Something went wrong. Please try again later.",
                Instant.now().getEpochSecond()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
