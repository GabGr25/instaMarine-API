package com.marine.instamarineapi.s3;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Service
public class S3Service {

    private final String bucketName = System.getenv("AWS_S3_BUCKET");
    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

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