package com.example.PixelPioneers.Service;

import com.amazonaws.SdkClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {
    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;

    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile multipartFile, String dir_name) throws Exception {

            String originalName = dir_name + "/" + multipartFile.getOriginalFilename(); // 파일 이름
            long size = multipartFile.getSize(); // 파일 크기

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(multipartFile.getContentType());
            objectMetaData.setContentLength(size);

            // S3에 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근가능한 URL 가져오기


        return imagePath;
    }

    public void deleteFile(String fileName) throws IOException {
        try{
            amazonS3Client.deleteObject(S3Bucket, fileName);
        } catch (SdkClientException e) {
            throw  new IOException("Error deleting file from S3", e);
        }
    }
}