package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.ContactListModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactListRepo {

    private final EntityManager em;

    @Autowired
    public ContactListRepo(EntityManager em) {
        this.em = em;
    }

    public List<ContactListModel> GetAll() {
        return em.createQuery("SELECT c FROM ContactListModel c", ContactListModel.class).getResultList();
    }

    public void Save(ContactListModel model) {
        em.persist(model);
    }

    public void Delete(int id) {
        ContactListModel model = em.find(ContactListModel.class, id);
        if (model != null) {
            em.remove(model);
        }
    }

    public ContactListModel fineById(int id) {
        return em.find(ContactListModel.class, id);
    }

    public ContactListModel findByName(String name) {
        return em.createQuery("SELECT c FROM ContactListModel c WHERE c.name = :name", ContactListModel.class).setParameter("name", name).getSingleResult();
    }

    public void Update(ContactListModel model) {
        em.merge(model);
    }

}
