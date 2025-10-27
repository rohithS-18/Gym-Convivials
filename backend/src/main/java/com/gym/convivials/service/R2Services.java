package com.gym.convivials.service;

import com.gym.convivials.configs.CloudflareProps;
import com.gym.convivials.configs.R2Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class R2Services {
    @Autowired
    private R2Config r2Config;
    @Autowired
    private CloudflareProps cloudflareProps;

    public String uploasPost(String category,String username,MultipartFile image) throws IOException {
        S3Client r2Bucket=r2Config.getR2Client();
//        String objectKey="uploads/"+image.getOriginalFilename();
        String objectKey=generateKey("dev",category,username,image.getOriginalFilename());
        String bucketName="user-media";
        String accountId=cloudflareProps.getAccountId();
        PutObjectResponse res=r2Bucket.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .contentType(image.getContentType())
                            .build(),
                RequestBody.fromBytes(image.getBytes())
                );
        String imageUrl="https://"+bucketName+"."+accountId + ".r2.cloudflarestorage.com/"+objectKey;
        return imageUrl;
    }

    private static String generateKey(String environment, String category, String username, String originalFilename) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
        String extension = "";

        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }

        String uniqueId = UUID.randomUUID().toString();

        return String.format("%s/%s/%s/%s_%s%s",
                environment, category, username, timestamp, uniqueId, extension);
    }

}
