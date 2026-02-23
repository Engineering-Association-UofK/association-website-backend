package edu.uofk.ea.association_website_backend.model.generics;


import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GenericRequest {

    @NotBlank(message = "Keyword cannot be blank.")
    @Size(min = 3,max = 50, message = "Keyword cannot be less than 3 characters or exceed 50 characters.")
    private String keyword;

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 255, message = "Title cannot exceed 255 characters.")
    private String title;

    @NotBlank(message = "Body cannot be blank.")
    private String body;

    @NotNull(message = "Language cannot be null.")
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
