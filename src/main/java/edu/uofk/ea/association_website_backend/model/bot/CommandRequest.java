package edu.uofk.ea.association_website_backend.model.bot;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommandRequest {

    @NotNull(message = "Language cannot be null.")
    private Language lang;

    @NotBlank(message = "Keyword cannot be blank.")
    @Size(min = 3, max = 50, message = "Keyword must be at least 3 characters long, and must not exceed 50 characters.")
    private String keyword;

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
