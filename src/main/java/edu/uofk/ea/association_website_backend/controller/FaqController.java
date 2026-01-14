package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.faq.FaqDashboardResponse;
import edu.uofk.ea.association_website_backend.model.faq.FaqGetModel;
import edu.uofk.ea.association_website_backend.model.faq.FaqTranslationModel;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/faqs")
public class FaqController {

    private final FaqService service;

    @Autowired
    public FaqController(FaqService service) {
        this.service = service;
    }

    @GetMapping
    public List<FaqTranslationModel> getFaqs(@RequestParam Language lang) {
        return service.getAllWithLang(lang);
    }

    @GetMapping("/dashboard")
    public List<FaqDashboardResponse> getDashboardFaqs(@RequestParam Language lang) {
        return service.dashboard(lang);
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
