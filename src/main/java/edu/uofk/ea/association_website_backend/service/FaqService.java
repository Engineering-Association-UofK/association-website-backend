package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.FaqModel;
import edu.uofk.ea.association_website_backend.repository.FaqRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {

    private FaqRepo repo;

    @Autowired
    public FaqService(FaqRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void save(FaqModel faq) {
        repo.save(faq);
    }

    public FaqModel findById(int id) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("FAQ not found with ID:" + id);
        }
        return repo.findById(id);
    }

    public List<FaqModel> getAll() {
        return repo.getAll();
    }

    @Transactional
    public void update(FaqModel faq) {
        if (repo.findById(faq.getId()) == null) {
            throw new GenericNotFoundException("FAQ not found with ID:" + faq.getId());
        }

        repo.update(faq);
    }

    @Transactional
    public void delete(int id) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("FAQ not found with ID:" + id);
        }
        repo.delete(id);
    }
}
