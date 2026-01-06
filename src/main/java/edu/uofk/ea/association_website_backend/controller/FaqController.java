package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.annotations.RateLimited;
import edu.uofk.ea.association_website_backend.model.FaqTranslationModel;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @RateLimited(key = "resource", capacity = 5, refillTokens = 5, refillDuration = 120)
    public List<FaqTranslationModel> getFaqs(@RequestParam Language lang) {
        return service.getAll(lang);
    }

    @GetMapping("/{id}")
    public FaqTranslationModel getFaq(@PathVariable int id, @RequestParam Language lang) {
        return service.findById(id, lang);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void addFaq(@RequestBody FaqTranslationModel faq) {
        service.save(faq);
    }


    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void updateFaq(@RequestBody FaqTranslationModel faq) {
        service.update(faq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void deleteFaq(@PathVariable int id) {
        service.delete(id);
    }
}
