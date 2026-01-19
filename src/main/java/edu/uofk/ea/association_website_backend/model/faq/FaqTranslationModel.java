package edu.uofk.ea.association_website_backend.model.faq;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.persistence.*;

@Entity
@Table(name="faqs_translations")
public class FaqTranslationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "faq_id")
    private int faqId;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Language lang;

    public FaqTranslationModel() {}

    public FaqTranslationModel(int faqId, String title, String body, Language translation) {
        this.faqId = faqId;
        this.title = title;
        this.body = body;
        this.lang = translation;
    }

    public FaqTranslationModel(String title, String body, Language translation){
        this.title = title;
        this.body = body;
        this.lang = translation;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getFaqId() {
        return faqId;
    }

    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Faq{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
