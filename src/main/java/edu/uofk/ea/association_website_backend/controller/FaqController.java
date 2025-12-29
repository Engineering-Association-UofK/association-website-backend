package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.FaqModel;
import edu.uofk.ea.association_website_backend.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void addFaq(@RequestBody FaqModel faq) {
        service.save(faq);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void updateFaq(@PathVariable int id, @RequestBody FaqModel faq) {
        faq.setId(id);
        service.update(faq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void deleteFaq(@PathVariable int id) {
        service.delete(id);
    }
}
