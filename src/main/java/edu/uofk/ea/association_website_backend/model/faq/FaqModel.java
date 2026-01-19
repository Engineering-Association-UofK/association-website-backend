package edu.uofk.ea.association_website_backend.model.faq;

import jakarta.persistence.*;

@Entity
@Table(name="faqs")
public class FaqModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public FaqModel() {}

    public FaqModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
