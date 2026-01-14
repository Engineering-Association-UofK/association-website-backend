package edu.uofk.ea.association_website_backend.model.faq;

import edu.uofk.ea.association_website_backend.model.Language;
import java.util.List;

public class FaqDashboardResponse {
    private int id;
    private String title;
    private List<Language> availableLanguages;

    public FaqDashboardResponse(int id, String title, List<Language> availableLanguages) {
        this.id = id;
        this.title = title;
        this.availableLanguages = availableLanguages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Language> getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(List<Language> availableLanguages) {
        this.availableLanguages = availableLanguages;
    }
}