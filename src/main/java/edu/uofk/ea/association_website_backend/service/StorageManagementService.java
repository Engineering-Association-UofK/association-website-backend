package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnexpectedErrorException;
import edu.uofk.ea.association_website_backend.model.storage.StorageModel;
import edu.uofk.ea.association_website_backend.model.storage.StorageReferenceModel;
import edu.uofk.ea.association_website_backend.model.storage.StoreType;
import edu.uofk.ea.association_website_backend.repository.StorageRepo;
import edu.uofk.ea.association_website_backend.repository.StorageReferenceRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageManagementService {

    private final StorageRepo storageRepo;
    private final StorageReferenceRepo referenceRepo;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public StorageManagementService(StorageRepo storageRepo, StorageReferenceRepo referenceRepo, CloudinaryService cloudinaryService) {
        this.storageRepo = storageRepo;
        this.referenceRepo = referenceRepo;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public void linkImageToEntity(String publicId, String url, String entityType, int entityId) {
        // Find or create the Storage record
        StorageModel storage = storageRepo.findByPublicId(publicId);
        if (storage == null) {
            storage = new StorageModel(StoreType.IMAGE, publicId, url);
            storageRepo.store(storage);
        }

        // Check if this entity already has an image linked
        StorageReferenceModel existingRef = referenceRepo.findByEntity(entityType, entityId);
        if (existingRef != null) {
            // If linked to the same image, do nothing.
            if (existingRef.getStorageId() == storage.getId()) {
                return;
            }

            // If linked to a different image, decrement the old one's count
            StorageModel oldStorage = storageRepo.findById(existingRef.getStorageId());
            if (oldStorage != null) {
                boolean decremented = storageRepo.decrementReferenceNum(oldStorage);
                if (!decremented) {
                    throw new UnexpectedErrorException("Reference count cannot be negative for storage ID: " + oldStorage.getId());
                }
            }

            // Update the reference
            existingRef.setStorageId(storage.getId());
            referenceRepo.update(existingRef);
        } else {
            // Create a new reference
            StorageReferenceModel newRef = new StorageReferenceModel(storage.getId(), entityType, entityId);
            referenceRepo.save(newRef);
        }

        // Increment the new image's reference count
        storageRepo.incrementReferenceNum(storage);
    }

    @Transactional
    public void unlinkImageFromEntity(String entityType, int entityId) {
        StorageReferenceModel reference = referenceRepo.findByEntity(entityType, entityId);

        if (reference != null) {
            StorageModel storage = storageRepo.findById(reference.getStorageId());
            if (storage != null) {
                storageRepo.decrementReferenceNum(storage);
            }
            referenceRepo.delete(reference);
        }
    }

    public void cleanupOrphanedImages(StoreType type) {
        List<StorageModel> orphans = storageRepo.getByReferenceNumAndType(0, type);

        for (StorageModel orphan : orphans) {
            if (cloudinaryService.deleteFile(orphan.getPublicId())) {
                storageRepo.delete(orphan);
            }
        }
    }
}