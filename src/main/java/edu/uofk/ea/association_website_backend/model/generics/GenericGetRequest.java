package edu.uofk.ea.association_website_backend.model.generics;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class GenericGetRequest {

    @NotBlank
    private String keyword;

    @NotBlank
    private Language lang;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}