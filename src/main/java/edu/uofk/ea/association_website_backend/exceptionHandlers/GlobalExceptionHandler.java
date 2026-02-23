package edu.uofk.ea.association_website_backend.exceptionHandlers;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericAlreadyExistsException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.WrongRequestBodyException;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<BaseErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        BaseErrorResponse error = new BaseErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), Instant.now().getEpochSecond());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        BaseErrorResponse error = new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), Instant.now().getEpochSecond());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleGenericNotFoundException(GenericNotFoundException e) {
        BaseErrorResponse error = new BaseErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), Instant.now().getEpochSecond());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericAlreadyExistsException.class)
    public ResponseEntity<BaseErrorResponse> handleGenericAlreadyExistsException(GenericAlreadyExistsException e) {
        BaseErrorResponse error = new BaseErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), Instant.now().getEpochSecond());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongRequestBodyException.class)
    public ResponseEntity<BaseErrorResponse> handleWrongRequestBodyException(WrongRequestBodyException e) {
        BaseErrorResponse error = new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), Instant.now().getEpochSecond());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("message", "Validation failed");
        errorBody.put("errors", errors);
        errorBody.put("timestamp", Instant.now().getEpochSecond());

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON request. Check your request body for syntax errors.",
                Instant.now().getEpochSecond()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<BaseErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        BaseErrorResponse error = new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), message, Instant.now().getEpochSecond());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /// a catch-all for any other exception.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResponse> handleAllOtherExceptions(Exception e) {
        logger.error("An unexpected error occurred: ", e);

        BaseErrorResponse error = new BaseErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                Instant.now().getEpochSecond()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
