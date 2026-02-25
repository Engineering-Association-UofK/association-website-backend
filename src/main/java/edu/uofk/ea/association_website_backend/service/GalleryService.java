package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.gallery.*;
import edu.uofk.ea.association_website_backend.model.storage.StorageModel;
import edu.uofk.ea.association_website_backend.model.storage.StoreType;
import edu.uofk.ea.association_website_backend.repository.StorageRepo;
import edu.uofk.ea.association_website_backend.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryService {

    private final StorageManagementService storageManagementService;
    private final StorageRepo storageRepo;
    private final NewsRepo newsRepo;

    @Autowired
    public GalleryService(StorageManagementService storageManagementService, StorageRepo storageRepo, NewsRepo newsRepo) {
        this.storageManagementService = storageManagementService;
        this.storageRepo = storageRepo;
        this.newsRepo = newsRepo;
    }

    public void AddImage(GalleryRequest request) {
        if (storageRepo.findByPublicId(request.getPublicId()) != null) throw new IllegalArgumentException("Image already exists");
        StorageModel model = new StorageModel(StoreType.IMAGE, request.getPublicId(), request.getUrl());
        storageRepo.store(model);
    }

    public List<GalleryResponse> getAll() {
        var models = storageRepo.getByLatest();
        var news = newsRepo.getAll();
        List<GalleryResponse> responses = new ArrayList<>();
        for (StorageModel m : models) {
            GalleryResponse r = new GalleryResponse(
                    m.getId(),
                    m.getUrl(),
                    m.getCreatedAt(),
                    news.stream().anyMatch(n -> n.getStorageId() == m.getId())
            );
            responses.add(r);
        }
        return responses;
    }

    public List<NewsResponse> getNews() {
        List<NewsModel> models = newsRepo.getAll();
        if (models.isEmpty()) return new ArrayList<>();

        List<Integer> ids = models.stream().map(NewsModel::getStorageId).toList();
        List<StorageModel> storageModels = storageRepo.getByList(ids);
        List<NewsResponse> responses = new ArrayList<>();

        for (NewsModel m : models) {
            storageModels.stream()
                    .filter(s -> s.getId() == m.getStorageId())
                    .findFirst()
                    .ifPresent(s -> responses.add(new NewsResponse(s.getUrl(), m.getAlt())));
        }
        return responses;
    }

    public void makeNews(int storageId, String alt) {
        StorageModel storage = storageRepo.findById(storageId);
        if (storage == null) throw new IllegalStateException("Storage item does not exist");
        if (storage.getType() != StoreType.IMAGE) throw new IllegalStateException("Storage item is not an image");
        if (newsRepo.findByStorageId(storageId) != null) throw new IllegalStateException("News already exists");

        NewsModel news = new NewsModel(storageId, alt);
        newsRepo.save(news);

        storageManagementService.linkImageToEntity(
                storage.getPublicId(),
                storage.getUrl(),
                edu.uofk.ea.association_website_backend.model.EntityType.NEWS.name(),
                news.getId()
        );
    }

    public void deleteNews(int storageId) {
        NewsModel news = newsRepo.findByStorageId(storageId);
        if (news == null) throw new IllegalStateException("News does not exist");

        storageManagementService.unlinkImageFromEntity(
                edu.uofk.ea.association_website_backend.model.EntityType.NEWS.name(),
                news.getId()
        );
        newsRepo.delete(news.getId());
    }

    public void deleteUnusedImages(StoreType type) {
        storageManagementService.cleanupOrphanedImages(type);
    }
}
