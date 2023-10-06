package com.demo.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import jdk.internal.org.jline.utils.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Storage {

    private String uploadPath;
    private String accessKey;
    private String secretKey;
    private String serviceEndpoint;
    private String signingRegion;
    private final AmazonS3 s3client;


    public Storage(String uploadPath, String accessKey, String secretKey, String serviceEndpoint, String signingRegion) {
        this.uploadPath = uploadPath;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.serviceEndpoint = serviceEndpoint;
        this.signingRegion = signingRegion;
        s3client = connect();
    }

    private AmazonS3 connect() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                serviceEndpoint, signingRegion
                        )
                )
                .build();
    }

    public List<Bucket> bucketList(String bucketName) {
        return s3client.listBuckets();
    }

    public boolean createBucket(String bucketName) {

        if (!s3client.doesBucketExistV2(bucketName)) {
            Log.info("имена не должны содержать подчеркивания\n" +
                    "имена должны быть длиной от 3 до 63 символов\n" +
                    "имена не должны заканчиваться тире\n" +
                    "имена не могут содержать смежные периоды\n" +
                    "имена не могут содержать дефисы рядом с точками (например, «my-.bucket.com» и «my.-bucket» недействительны)\n" +
                    "имена не могут содержать символы верхнего регистра");
            return false;
        }
        s3client.createBucket(bucketName);
        return true;
    }

    public void addFile(String bucketName, MultipartFile multipartFile) throws Exception {

        File file = new File(System.getProperty("java.io.tmpdir"), multipartFile.getOriginalFilename());
        multipartFile.transferTo(file.getAbsoluteFile());
        s3client.putObject(bucketName,
                multipartFile.getOriginalFilename(),
                file.getAbsoluteFile());
        file.delete();
    }

    public void deleteFile(String bucketName, String fileName) {
        s3client.deleteObject(bucketName, fileName);
    }

    public String getUrl(String fileName, String bucketName) {
        return s3client.getUrl(bucketName, fileName).toString();
    }

    public void downloadFile(String bucketName, String fileName) throws IOException {
        S3Object s3object = s3client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File(uploadPath + fileName));
    }

    public void fileList(String bucketName) {
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            Log.info(os.getKey());
        }
    }
}
