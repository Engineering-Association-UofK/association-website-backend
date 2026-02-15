package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericAlreadyExistsException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.model.generics.*;
import edu.uofk.ea.association_website_backend.repository.GenericsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenericsService {

    private final GenericsRepo repo;

    @Autowired
    public GenericsService(GenericsRepo repo) {
        this.repo = repo;
    }

    public GenericResponse getGeneric(GenericGetRequest request) {
        GenericModel model = repo.findByKeyword(request.getKeyword());
        if (model == null) {
            throw new GenericNotFoundException("Text not found, Make sure you added a valid field.");
        }
        int id = model.getId();

        GenericTranslationModel translation = repo.findTById(id, request.getLang());
        if (translation == null) {
            translation = repo.findTById(id, Language.en);
            if (translation == null) {
                throw new GenericNotFoundException("Text not found, Make sure you added a valid field.");
            }
        }
        return new GenericResponse(request.getKeyword() ,translation.getTitle(), translation.getBody());
    }

    @Transactional
    public void update(GenericRequest request) {
        GenericModel model = repo.findByKeyword(request.getKeyword());
        if (model == null) {
            throw new GenericNotFoundException("Text not found, Make sure you added a valid field.");
        }
        int id = model.getId();

        GenericTranslationModel translation = repo.findTById(id, request.getLang());
        if (translation != null) {
            translation.setTitle(request.getTitle());
            translation.setBody(request.getBody());
            repo.updateTranslation(translation);
        }
        else {
            repo.saveTranslation(new GenericTranslationModel(id, request.getTitle(), request.getBody(), request.getLang()));
        }
    }

    @Transactional
    public void Save(GenericRequest request) {
        if (repo.findByKeyword(request.getKeyword()) != null) {
            throw new GenericAlreadyExistsException("Text already exists");
        }
        GenericModel model = new GenericModel(request.getKeyword());
        repo.save(model);
        update(request);
    }

    @Transactional
    public void delete(int id) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Text not found, Make sure you added a valid field.");
        }
        repo.delete(id);
    }

    public void SaveAll(List<GenericRequest> request) {
        for (GenericRequest r : request) {
            Save(r);
        }
    }

    public List<GenericResponse> GetBatchGeneric(GenericBatchRequest request) {
        List<GenericModel> models = repo.findByKeywords(request.getKeywords());
        List<GenericResponse> responses = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            int id = models.get(i).getId();
            GenericTranslationModel translation = repo.findTById(id, request.getLang());
            if (translation == null) {
                translation = repo.findTById(id, Language.en);
                if (translation == null) {
                    throw new GenericNotFoundException("Text not found, Make sure you added a valid field.");
                }
            }
            responses.add(new GenericResponse(models.get(i).getKeyword(), translation.getTitle(), translation.getBody()));
        }
        return responses;
    }

    public void updateAll(List<GenericRequest> request) {
        for (GenericRequest r : request) {
            update(r);
        }
    }
}
