package com.marine.instamarineapi.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    private final String bucketName = System.getenv("AWS_S3_BUCKET");

    public String uploadFile(String key, InputStream inputStream, long contentLength) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromInputStream(inputStream, contentLength)
        );

        return "https://" + bucketName + ".s3.eu-west-3.amazonaws.com/" + key;
    }
}