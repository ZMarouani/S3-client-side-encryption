package com.aws.s3encryption;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        var usEast1 = "us-east-1";
        var clientRegion = Regions.fromName(usEast1);
        var bucketName = "terraformbackend00";
        var fileObjKeyName = "fileForEncryption";
        var stringObjKeyName = "keyNameForEncryption";
        var fileName = "./file.txt";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                                     .withRegion(clientRegion)
                                                     .build();

            //Upload a string object
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            //Upload a file
            var request = new PutObjectRequest(bucketName, fileObjKeyName, new
                    File(fileName));
            var metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "EncryptMe");
            request.setMetadata(metadata);
            s3Client.putObject(request);

        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
