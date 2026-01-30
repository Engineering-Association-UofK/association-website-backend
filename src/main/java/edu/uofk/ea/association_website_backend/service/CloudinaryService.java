package edu.uofk.ea.association_website_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.model.CloudinaryRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Uploads a file to Cloudinary from the backend.
     *
     * @param fileBytes The raw bytes of the file to upload.
     * @param publicId  The desired public ID (filename) for the asset in Cloudinary. This should be unique.
     * @return The secure URL of the uploaded file.
     */
    public String uploadDoc(byte[] fileBytes, String publicId) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "raw",
                    "type", "upload",
                    "public_id", "Document:" + publicId + ".pdf",
                    "overwrite", true
            );

            Map<?, ?> uploadResult = cloudinary.uploader().upload(fileBytes, options);

            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }

    public CloudinaryRequestModel validateAndSign(CloudinaryRequestModel request) {
        if (request.getApiKey() == null || !isValidApiKey(request.getApiKey())) throw new UnauthorizedException("Invalid API key");

        if (request.getTimestamp() == null) throw new IllegalArgumentException("No Timestamp found.");
        if (Math.abs(Instant.now().getEpochSecond() - request.getTimestamp()) > 60) throw new IllegalArgumentException("Timestamp is not within the allowed window.");
        if (request.getCloudName() == null) throw new IllegalArgumentException("No Cloud name found.");
        if (request.getResourceType() == null) throw new IllegalArgumentException("Resource type not found.");

        return generateSignedRequest(request);
    }

    private CloudinaryRequestModel generateSignedRequest(CloudinaryRequestModel request) {

        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", request.getTimestamp());
        params.put("upload_preset", request.getUploadPreset());

        String signature = cloudinary.apiSignRequest(params, apiSecret, 1);

        request.setUploadSignature(signature);

        return request;
    }

    private boolean isValidApiKey(String apiKey) {
        return Objects.equals(apiKey, this.apiKey);
    }
}
