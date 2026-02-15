package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryDeletedModel;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemModel;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemType;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryKeywordResponse;
import edu.uofk.ea.association_website_backend.repository.GalleryDeletedRepo;
import edu.uofk.ea.association_website_backend.repository.GalleryItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class GalleryService {

    private final GalleryItemRepo repo;
    private final GalleryDeletedRepo deletedRepo;

    @Autowired
    public GalleryService(GalleryItemRepo repo, GalleryDeletedRepo deletedRepo) {
        this.repo = repo;
        this.deletedRepo = deletedRepo;
    }

    @Transactional
    public void save(GalleryItemModel item){
        item.setCreatedAt(Instant.now());

        if (item.getKeyword() != null && repo.findByKeyword(item.getKeyword()) != null) {
            throw new GenericNotFoundException("Items must have a unique keyword.");
        }

        if (item.getType() == GalleryItemType.store) {
            if (item.getKeyword() == null || item.getKeyword().isEmpty()) {
                throw new IllegalArgumentException("Store items must have a unique keyword.");
            }
        }

        repo.save(item);
    }

    public GalleryItemModel findById(int id){
        return repo.findById(id);
    }

    public GalleryKeywordResponse findByKeyword(String keyword){
        GalleryItemModel item = repo.findByKeyword(keyword);

        if (item == null) {
            throw new GenericNotFoundException("Gallery item not found with keyword: " + keyword);
        }

        return new GalleryKeywordResponse(
                item.getKeyword(),
                item.getTitle(),
                item.getImageLink()
        );
    }

    public List<GalleryItemModel> findByType(GalleryItemType type){
        return repo.findByType(type);
    }

    @Transactional
    public void update(GalleryItemModel item){
        if (repo.findById(item.getId()) == null) {
            if (item.getKeyword() != null) {
                GalleryItemModel model = repo.findByKeyword(item.getKeyword());
                if (model != null){
                    item.setId(model.getId());
                    if (!Objects.equals(model.getImageLink(), item.getImageLink())) {
                        deletedRepo.save(new GalleryDeletedModel(model.getImageLink()));
                    }
                    repo.Update(item);
                    return;
                }
            }
        }
        throw new GenericNotFoundException("Gallery item not found");
    }

    public List<GalleryItemModel> getAll(){
        return repo.getAll();
    }

    @Transactional
    public void delete(int id){
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Gallery item not found with ID:" + id);
        }

        GalleryDeletedModel model = new GalleryDeletedModel(repo.findById(id).getImageLink());
        deletedRepo.save(model);

        repo.delete(id);
    }
}
