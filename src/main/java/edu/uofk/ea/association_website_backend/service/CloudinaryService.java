package edu.uofk.ea.association_website_backend.service;

import com.cloudinary.Cloudinary;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.model.CloudinaryRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
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

        String signature = cloudinary.apiSignRequest(params, System.getenv("API_SECRET"), 1);

        request.setUploadSignature(signature);

        return request;
    }

    private boolean isValidApiKey(String apiKey) {
        return Objects.equals(apiKey, System.getenv("API_KEY"));
    }

}
