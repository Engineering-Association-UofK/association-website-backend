package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.blog.BlogPostModel;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogPostRepo {

    private EntityManager em;

    @Autowired
    public BlogPostRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(BlogPostModel post){
        em.persist(post);
        em.flush();
        em.refresh(post);
        return post.getId();
    }

    public BlogPostModel findById(int id){
        return em.find(BlogPostModel.class, id);
    }

    public List<BlogPostModel> getAll(){
        return em.createQuery("FROM BlogPostModel", BlogPostModel.class).getResultList();
    }

    @Transactional
    public void update(BlogPostModel post){
        em.merge(post);
    }

    @Transactional
    public void delete(int id){
        em.remove(findById(id));
    }
}
