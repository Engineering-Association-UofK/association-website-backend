package edu.uofk.ea.association_website_backend.model.bot;

import edu.uofk.ea.association_website_backend.model.Language;

public class CommandRequest {
    private Language lang;
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
