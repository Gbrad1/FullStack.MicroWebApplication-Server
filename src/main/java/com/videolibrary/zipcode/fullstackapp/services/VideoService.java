package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.aws.AmazonClient;
import com.videolibrary.zipcode.fullstackapp.models.Video;

import com.videolibrary.zipcode.fullstackapp.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private VideoRepository videoRepository;

    private AmazonClient s3client;

    @Autowired
    public VideoService(VideoRepository videoRepository, AmazonClient s3client) {
        this.videoRepository = videoRepository;
        this.s3client = s3client;
    }

    public Video create(Video v) {
        return videoRepository.save(v);
    }

    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    public List<Video> index() {
        return videoRepository.findAll();
    }

    public Video update(Long id) {
        Video video = videoRepository.getVideoById(id);
        video.setVideoTitle (video.getVideoTitle());
        videoRepository.save(video);
        return video;
    }

    public File convertMultiPartFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }

    //Could possibly rework
    public Video saveVideo(String videoName, MultipartFile multipartFile) throws Exception{
        String endPointUrl = "https:/videolibrary-video-bucket.s3.amazonaws.com";
        File file = convertMultiPartFile(multipartFile);
        Video video = new Video(videoName, multipartFile.getContentType());
        String fileName = generateFileName(file.getName());
        String fileUrl = endPointUrl + "/" + fileName;
        video.setVideoPath(fileUrl);
        if(uploadFile(file, fileName).isSuccessful()){
            return create(video);
        } else
            return null;
    }

    //stays the same
    public SdkHttpResponse uploadFile(File file, String fileName) throws S3Exception,
            AwsServiceException, SdkClientException, URISyntaxException, FileNotFoundException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3client.getBucket()).key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ_WRITE)
                .build();
        return s3client.generateAwsS3Client().putObject(putObjectRequest, RequestBody.fromFile(file)).sdkHttpResponse();
    }

    public boolean delete(Long videoId) throws Exception {
        return videoRepository.deleteVideoById(videoId);
    }

    //stays the same
    public DeleteObjectResponse deleteFile(String fileName, String videoPath) {
        DeleteObjectRequest deleteObjectResponse = DeleteObjectRequest.builder()
                .bucket(s3client.getBucket()).key(fileName).build();
        return s3client.generateAwsS3Client().deleteObject(deleteObjectResponse);
    }


    public String generateFileName(String fileName){
        return new Date().getTime() + "-" + fileName.replace(" ", "_");
    }
}
