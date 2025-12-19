package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.model.CloudinaryRequestModel;
import edu.uofk.ea.association_website_backend.service.CloudinaryService;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/sign")
public class CloudinaryController {

    private CloudinaryService service;

    @Autowired
    public CloudinaryController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping
    public CloudinaryRequestModel signRequest(@RequestBody CloudinaryRequestModel request) {
        return service.validateAndSign(request);
    }

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
