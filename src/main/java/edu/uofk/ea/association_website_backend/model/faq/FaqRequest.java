package edu.uofk.ea.association_website_backend.model.faq;

import java.util.List;

/// DTO for creating and updating FAQs.
public class FaqRequest {

    private Integer id; // Should be null for creation and non-null for updates.
    private List<FaqTranslationModel> translations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<FaqTranslationModel> getTranslations() {
        return translations;
    }

    public void setTranslations(List<FaqTranslationModel> translations) {
        this.translations = translations;
    }
}