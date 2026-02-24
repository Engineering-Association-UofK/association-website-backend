package edu.uofk.ea.association_website_backend.model.generics;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class GenericBatchRequest {

    @NotEmpty(message = "Keywords list cannot be empty.")
    private List<
            @NotBlank(message = "Keyword cannot be blank.")
            @Size(min = 3,max = 50, message = "Keyword cannot be less than 3 characters or exceed 50 characters.")
            String
            > keywords;

    @NotNull(message = "Language cannot be null.")
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
