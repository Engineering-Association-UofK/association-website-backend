package edu.uofk.ea.association_website_backend.model.generics;


import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;

public class GenericRequest {

    @NotBlank
    private String keyword;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private Language lang;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}
