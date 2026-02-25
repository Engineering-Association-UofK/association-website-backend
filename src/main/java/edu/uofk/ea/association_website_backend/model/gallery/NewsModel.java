package edu.uofk.ea.association_website_backend.model.gallery;

import jakarta.persistence.*;

@Entity
@Table(name="news")
public class NewsModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="storage_id")
    private int storageId;

    @Column(name="alt")
    private String alt;

    public NewsModel() {
    }

    public NewsModel(int storageId, String alt) {
        this.storageId = storageId;
        this.alt = alt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
