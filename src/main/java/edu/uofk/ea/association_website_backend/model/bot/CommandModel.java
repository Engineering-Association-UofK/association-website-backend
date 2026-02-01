package edu.uofk.ea.association_website_backend.model.bot;

import jakarta.persistence.*;
import edu.uofk.ea.association_website_backend.model.Language;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "bot_commands")
public class CommandModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "keyword")
    private String keyword;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bot_command_triggers", joinColumns = @JoinColumn(name = "command_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "language")
    @Column(name = "trigger_text")
    private Map<Language, String> triggers = new HashMap<>();

    @Column(name = "description")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bot_command_translations", joinColumns = @JoinColumn(name = "command_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "language")
    @Column(name = "text", columnDefinition = "TEXT")
    private Map<Language, String> texts = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bot_command_options", joinColumns = @JoinColumn(name = "command_id"))
    @Column(name = "next_keyword")
    private Set<String> nextKeywords = new HashSet<>();

    public CommandModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getTrigger(Language lang) {
        return triggers.getOrDefault(lang, triggers.get(Language.en));
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

    public String getText(Language lang) {
        return texts.getOrDefault(lang, texts.get(Language.en));
    }

    public Set<String> getNextKeywords() {
        return nextKeywords;
    }

    public void setNextKeywords(Set<String> nextKeywords) {
        this.nextKeywords = nextKeywords;
    }
}
