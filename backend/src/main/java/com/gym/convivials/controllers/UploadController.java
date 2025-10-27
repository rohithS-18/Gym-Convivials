package com.gym.convivials.controllers;

import com.gym.convivials.dto.PresignRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadController {
    private final S3Presigner presigner;
    private final String bucket;

    @Autowired
    public UploadController(S3Presigner presigner, @Value("${cloudflare.bucket}") String bucket) {
        this.presigner = presigner;
        this.bucket = bucket;
    }

    @PostMapping("/presign")
    public ResponseEntity<Map<String, Object>> presign(
            @RequestBody PresignRequest request // JSON { "filename": "...", "contentType":"...", "userId":123 }
    ) {
        String key = generateKey("dev",request.getCategory(),request.getUsername());

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(request.getContentType())
                .build();

        // expiration
        Duration expires = Duration.ofMinutes(5);
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(expires)
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("url", presignedRequest.url().toString());
        resp.put("method", "PUT");
        resp.put("key", key);
        resp.put("expiresInSeconds", expires.getSeconds());
        resp.put("headers", Map.of("Content-Type", request.getContentType())); // client should set this
        return ResponseEntity.ok(resp);
    }

    private static String generateKey(String environment, String category, String username) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
         String uniqueId = UUID.randomUUID().toString();
        return String.format("%s/%s/%s/%s_%s",
                environment, category, username, timestamp, uniqueId);
    }
}
