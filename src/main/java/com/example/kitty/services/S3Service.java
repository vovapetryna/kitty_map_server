package com.example.kitty.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final String STORAGE_BUCKET = "cloud-cube-us2";
    private final String CUBE_NAME = "hs96ia033t8m";
    private final AmazonS3 amazonS3;

    public File downloadFileToLocalStorage(String objectName) {
        File outputFile = new File("." + File.separator + objectName);
        System.out.println(outputFile.getAbsolutePath());

        if (outputFile.exists() && !outputFile.isDirectory()) {
            return outputFile;
        }

        S3Object s3object = amazonS3.getObject(STORAGE_BUCKET, CUBE_NAME + "/" + objectName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();

        try {
            FileUtils.copyInputStreamToFile(inputStream, outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return outputFile;
    }

    public void uploadLocalFileToStorage(String objectName) {
        File file = new File("." + File.separator + objectName);
        System.out.println(file.getAbsolutePath());
        System.out.println(amazonS3.putObject(STORAGE_BUCKET, CUBE_NAME + "/" + objectName, file));
    }

}
