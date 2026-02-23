package edu.uofk.ea.association_website_backend.model.bot;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;
import java.util.Set;

public class BotCommandDTO {
    private Integer id;

    @NotBlank(message = "Keyword cannot be blank.")
    @Size(min = 3, max = 50, message = "Keyword must be at least 3 characters long, and must not exceed 50 characters.")
    private String keyword;

    @NotEmpty(message = "Triggers cannot be empty.")
    private Map<
                Language,
                @Size(min = 3, max = 50, message = "Trigger must be at least 3 characters long, and must not exceed 50 characters.")
                String
            > triggers;

    @NotBlank(message = "Description cannot be blank.")
    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    @NotEmpty(message = "Texts cannot be empty.")
    private Map<
                Language,
                @NotEmpty(message = "Texts cannot be empty.")
                String
            > texts;

    private Set<
                @Size(min = 3, max = 50, message = "Keyword must be at least 3 characters long, and must not exceed 50 characters.")
                String
            > nextKeywords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Map<Language, String> getTriggers() {
        return triggers;
    }

    public void setTriggers(Map<Language, String> triggers) {
        this.triggers = triggers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Language, String> getTexts() {
        return texts;
    }

    public void setTexts(Map<Language, String> texts) {
        this.texts = texts;
    }

    public Set<String> getNextKeywords() {
        return nextKeywords;
    }

    public void setNextKeywords(Set<String> nextKeywords) {
        this.nextKeywords = nextKeywords;
    }
}