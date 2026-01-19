package edu.uofk.ea.association_website_backend.model.faq;

public class FaqSeeResponse {
    private int FaqId;
    private String title;
    private String body;

    public FaqSeeResponse(int faqId, String title, String body) {
        this.FaqId = faqId;
        this.title = title;
        this.body = body;
    }

    public int getFaqId() {
        return FaqId;
    }

    public void setFaqId(int faqId) {
        FaqId = faqId;
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
}
