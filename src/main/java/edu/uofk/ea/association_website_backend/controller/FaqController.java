package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.FaqModel;
import edu.uofk.ea.association_website_backend.service.FaqService;
import edu.uofk.ea.association_website_backend.util.BaseErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/faqs")
public class FaqController {

    private final FaqService service;

    @Autowired
    public FaqController(FaqService service) {
        this.service = service;
    }

    @GetMapping
    public List<FaqModel> getFaqs() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FaqModel getFaq(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    public void addFaq(@RequestBody FaqModel faq) {
        service.save(faq);
    }

    @PutMapping("/{id}")
    public void updateFaq(@PathVariable int id, @RequestBody FaqModel faq) {
        faq.setId(id);
        service.update(faq);
    }

    @DeleteMapping("/{id}")
    public void deleteFaq(@PathVariable int id) {
        service.delete(id);
    }

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
