package edu.uofk.ea.association_website_backend.model.bot;

public class CommandResponse {
    private String keyword;
    private String text;

    public CommandResponse(String keyword, String text) {
        this.keyword = keyword;
        this.text = text;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
