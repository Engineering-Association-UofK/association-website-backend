package edu.uofk.ea.association_website_backend.model.bot;

import edu.uofk.ea.association_website_backend.model.Language;
import java.util.Map;
import java.util.Set;

public class BotCommandDTO {
    private Integer id;
    private String keyword;
    private Map<Language, String> triggers;
    private String description;
    private Map<Language, String> texts;
    private Set<String> nextKeywords;

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