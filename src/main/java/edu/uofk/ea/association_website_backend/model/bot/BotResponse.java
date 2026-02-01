package edu.uofk.ea.association_website_backend.model.bot;

import java.util.List;

public class BotResponse {
    private String responseText;
    private boolean isFinal;
    private List<CommandOption> options;

    public BotResponse(String responseText, boolean isFinal, List<CommandOption> options) {
        this.responseText = responseText;
        this.isFinal = isFinal;
        this.options = options;
    }

    public String getResponseText() {
        return responseText;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public List<CommandOption> getOptions() {
        return options;
    }
}