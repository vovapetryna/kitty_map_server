package com.example.kitty.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class AWSConfig {
    @Bean
    public S3Client amazonS3() {
        String accessKey = System.getenv("CLOUDCUBE_ACCESS_KEY_ID");
        String secretKey = System.getenv("CLOUDCUBE_SECRET_ACCESS_KEY");

        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Client s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.EU_WEST_1)
                .build();
        return s3client;
    }
}