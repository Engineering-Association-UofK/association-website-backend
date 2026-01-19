package edu.uofk.ea.association_website_backend.model.faq;

import edu.uofk.ea.association_website_backend.model.Language;

import java.util.ArrayList;
import java.util.List;

public class FaqGetModel {
    private int id;
    private List<Language> langs;

    public FaqGetModel(int id) {
        this.id = id;
        this.langs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Language> getLangs() {
        return langs;
    }

    public void setLangs(List<Language> langs) {
        this.langs = langs;
    }
}
