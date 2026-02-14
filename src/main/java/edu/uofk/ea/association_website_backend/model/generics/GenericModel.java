package edu.uofk.ea.association_website_backend.model.generics;

import jakarta.persistence.*;

@Entity
@Table(name="generics")
public class GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "keyword")
    private String keyword;

    public GenericModel() {}

    public GenericModel(String keyword) {
        this.keyword = keyword;
    }

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
}
