package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.TeamMemberModel;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepo {

    private final EntityManager em;

    @Autowired
    public TeamRepo(EntityManager em) {
        this.em = em;
    }

    public List<TeamMemberModel> getAll() {
        return em.createQuery("FROM TeamMemberModel", TeamMemberModel.class).getResultList();
    }

    public TeamMemberModel findById(int id){
        return em.find(TeamMemberModel.class, id);
    }

    @Transactional
    public void save(TeamMemberModel member){
        em.persist(member);
    }

    @Transactional
    public void update(TeamMemberModel model) {
        em.merge(model);
    }

    @Transactional
    public void delete(int id) {
        em.remove(findById(id));
    }
}
