package com.rentalCar.images;

import com.rentalCar.car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public void deleteOldImages(Car existingCar, String bucketName) throws IOException {

        if (existingCar.getImageUrl() != null && !existingCar.getImageUrl().isEmpty()) {
            String imageUrl = existingCar.getImageUrl();
            String objectKey = extractObjectKeyFromUrl(imageUrl);
            s3Client.deleteObject(bucketName, objectKey);
        }

        if (existingCar.getDetailsImages() != null && !existingCar.getDetailsImages().isEmpty()) {
            for (String detailImageUrl : existingCar.getDetailsImages()) {
                String objectKey = extractObjectKeyFromUrl(detailImageUrl);
                s3Client.deleteObject(bucketName, objectKey);
            }
        }
    }

    public String uploadFile(MultipartFile file, String bucketName) throws IOException {
        String key = generateFileName(file);

        Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
        Files.write(tempFile, file.getBytes(), StandardOpenOption.CREATE);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(bucketName, key, file.getInputStream(), null);


        Files.delete(tempFile);

        return "https://"+ bucketName +".s3.amazonaws.com/" + key;
    }

    private String generateFileName(MultipartFile file) {
        return System.currentTimeMillis() + "_" + file.getOriginalFilename();
    }

    private String extractObjectKeyFromUrl(String url) {
        // Assuming your URL structure is like "https://bucketName.s3.amazonaws.com/path/to/object/key.jpg"
        int startIndex = url.indexOf(".s3.amazonaws.com/") + 20; // Skip base URL part
        return url.substring(startIndex);
    }

}

