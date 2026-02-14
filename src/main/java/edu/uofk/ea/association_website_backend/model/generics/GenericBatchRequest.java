package edu.uofk.ea.association_website_backend.model.generics;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class GenericBatchRequest {

    private List<String> keywords;

    @NotBlank
    private Language lang;

    public GenericBatchRequest() {
    }

    public GenericBatchRequest(List<String> keywords, Language lang) {
        this.keywords = keywords;
        this.lang = lang;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

}
