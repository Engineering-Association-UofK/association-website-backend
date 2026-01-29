package edu.uofk.ea.association_website_backend.model.faq;

import java.util.List;

public class FaqDashboardResponse {
    private int faqId;
    private String title;
    private String body;
    private List<FaqTranslationModel> translations;

    public FaqDashboardResponse(int faqId,String title, String body, List<FaqTranslationModel> translations) {
        this.faqId = faqId;
        this.title = title;
        this.body = body;
        this.translations = translations;
    }

    public FaqDashboardResponse(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public int getFaqId() {
        return faqId;
    }

    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<FaqTranslationModel> getTranslations() {
        return translations;
    }

    public void setTranslations(List<FaqTranslationModel> translations) {
        this.translations = translations;
    }
}