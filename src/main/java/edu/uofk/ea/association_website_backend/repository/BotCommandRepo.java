package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.bot.CommandModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class BotCommandRepo {

    @PersistenceContext
    private EntityManager em;

    public CommandModel findByKeyword(String keyword) {
        try {
            TypedQuery<CommandModel> query = em.createQuery("SELECT c FROM CommandModel c WHERE c.keyword = :keyword", CommandModel.class);
            query.setParameter("keyword", keyword);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CommandModel> findAll() {
        TypedQuery<CommandModel> query = em.createQuery("SELECT c FROM CommandModel c", CommandModel.class);
        return query.getResultList();
    }

    public List<CommandModel> findAllByKeywordIn(Set<String> keywords) {
        if (keywords == null || keywords.isEmpty()) return Collections.emptyList();
        TypedQuery<CommandModel> query = em.createQuery("SELECT c FROM CommandModel c WHERE c.keyword IN :keywords", CommandModel.class);
        query.setParameter("keywords", keywords);
        return query.getResultList();
    }

    public CommandModel findById(int id) {
        return em.find(CommandModel.class, id);
    }

    public CommandModel findByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) return null;

        TypedQuery<CommandModel> query = em.createQuery("SELECT c FROM CommandModel c WHERE c.id IN :ids", CommandModel.class);
        query.setParameter("ids", ids);
        return query.getSingleResult();
    }

    @Transactional
    public void save(CommandModel command) {
        em.persist(command);
    }

    @Transactional
    public void update(CommandModel command) {
        em.merge(command);
    }

    @Transactional
    public void delete(int id) {
        CommandModel c = em.find(CommandModel.class, id);
        if (c != null) em.remove(c);
    }
}