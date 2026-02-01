package edu.uofk.ea.association_website_backend.model.bot;

public class CommandOption {
    private String trigger; // The text to display on the button
    private String keyword; // The keyword to send in the next request

    public CommandOption(String trigger, String keyword) {
        this.trigger = trigger;
        this.keyword = keyword;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getKeyword() {
        return keyword;
    }
}