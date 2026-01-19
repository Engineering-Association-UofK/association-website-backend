package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.faq.FaqDashboardResponse;
import edu.uofk.ea.association_website_backend.model.faq.FaqRequest;
import edu.uofk.ea.association_website_backend.model.faq.FaqSeeResponse;
import edu.uofk.ea.association_website_backend.model.faq.FaqTranslationModel;
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

    /// Get just fetches all the available FAQs in the provided language
    /// Used for things like the FAQ component in the frontend
    @GetMapping
    public List<FaqTranslationModel> getFaqs(@RequestParam Language lang) {
        return service.getAllWithLang(lang);
    }

    /// "See" for the admin dashboard where they are shown with the needed details
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public List<FaqSeeResponse> seeFaqs() {
        return service.seeAll();
    }

    @GetMapping("/dashboard/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public FaqDashboardResponse seeFaq(@PathVariable int id) {
        return service.seeOne(id);
    }

    /// Transactional operations
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void addFaq(@RequestBody FaqRequest faq) {
        service.save(faq);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void updateFaq(@RequestBody FaqRequest faq) {
        service.update(faq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void deleteFaq(@PathVariable int id) {
        service.delete(id);
    }
}
