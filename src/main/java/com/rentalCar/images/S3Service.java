package com.rentalCar.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Autowired
    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String bucketName) throws IOException {
        String key = generateFileName(file);

        // Convert MultipartFile to a temporary file
        Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
        Files.write(tempFile, file.getBytes(), StandardOpenOption.CREATE);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(bucketName, key, file.getInputStream(), null);

        // Delete the temporary file
        Files.delete(tempFile);

        return "https://s3.amazonaws.com/" + bucketName + "/" + key;
    }

    private String generateFileName(MultipartFile file) {
        return System.currentTimeMillis() + "_" + file.getOriginalFilename();
    }
}

