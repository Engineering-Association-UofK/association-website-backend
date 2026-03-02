package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.event.EventComponentModel;
import edu.uofk.ea.association_website_backend.model.student.StudentModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentRepo {

    private final EntityManager em;

    @Autowired
    public StudentRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(StudentModel event) {
        em.persist(event);
        em.flush();
        em.refresh(event);
        return event.getId();
    }

    public StudentModel findById(int id) {
        return em.find(StudentModel.class, id);
    }

    public List<StudentModel> findAllById(List<Integer> ids) {
        return em.createQuery("SELECT s FROM StudentModel s WHERE s.id IN :ids", StudentModel.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Transactional
    public void update(StudentModel event) {
        em.merge(event);
    }

    @Transactional
    public void delete(int id) {
        em.remove(findById(id));
    }
}
