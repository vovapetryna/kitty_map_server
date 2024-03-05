package com.example.kitty.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final String STORAGE_BUCKET = Optional.ofNullable(System.getenv("STORAGE_BUCKET")).orElse("cloud-cube-eu2");
    private final String CUBE_NAME = Optional.ofNullable(System.getenv("CUBE_NAME")).orElse("kowt1ftgxch6");
    private final S3Client amazonS3;

    public File downloadFileToLocalStorage(String objectName) {
        File outputFile = new File("." + File.separator + objectName);
        System.out.println(outputFile.getAbsolutePath());

        if (outputFile.exists() && !outputFile.isDirectory()) {
            return outputFile;
        }

        var response = amazonS3.getObject(GetObjectRequest.builder()
                .bucket(STORAGE_BUCKET)
                .key(CUBE_NAME + "/" + objectName)
                .build());

        try {
            FileUtils.copyInputStreamToFile(response, outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return outputFile;
    }

    public void uploadLocalFileToStorage(String objectName) {
        File file = new File("." + File.separator + objectName);
        System.out.println(file.getAbsolutePath());

        var result = amazonS3.putObject(PutObjectRequest.builder()
                .bucket(STORAGE_BUCKET)
                .key(CUBE_NAME + "/" + objectName)
                .build(), Path.of(file.toURI()));

        System.out.println(result);

//        System.out.println(amazonS3.putObject(new PutObjectRequest(STORAGE_BUCKET, CUBE_NAME + "/" + objectName, file)));

//        System.out.println(amazonS3.putObject(STORAGE_BUCKET, CUBE_NAME + "/" + objectName, file));
    }

}
