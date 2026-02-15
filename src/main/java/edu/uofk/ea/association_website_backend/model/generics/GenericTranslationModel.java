package edu.uofk.ea.association_website_backend.model.generics;

import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.persistence.*;

@Entity
@Table(name="generic_translations")
public class GenericTranslationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "gen_id")
    private int genId;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Language lang;

    public GenericTranslationModel() {}

    public GenericTranslationModel(int genId, String title, String body, Language translation) {
        this.genId = genId;
        this.title = title;
        this.body = body;
        this.lang = translation;
    }

    public GenericTranslationModel(String title, String body, Language translation){
        this.title = title;
        this.body = body;
        this.lang = translation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getGenId() {
        return genId;
    }

    public void setGenId(int genId) {
        this.genId = genId;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Gen{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
