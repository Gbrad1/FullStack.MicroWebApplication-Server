package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.services.CommentService;
import com.videolibrary.zipcode.fullstackapp.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/video/")
@CrossOrigin(origins = {"http://vidstack.herokuapp.com", "http://localhost:4200"})
public class VideoController {

    private VideoService service;
    private CommentService commentService;
    private Comment comment;

    @Autowired
    public VideoController(VideoService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(service.findAllVideos(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return this.service.findVideo(id)
                .map(video -> ResponseEntity
                        .ok()
                        .body (video))
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping("create")
    public ResponseEntity<Video> create(@RequestBody Video v) {
        Video video = service.create(v);

            try {
                return ResponseEntity
                        .created ( new URI ( "/video/" + video.getId () ) )
                        .body ( video );
            } catch (URISyntaxException e) {
                return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR ).build();
            }
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Video v) {
        Optional<Video> currentVideo = service.findVideo(id);

        return currentVideo
                .map(video -> {
                    video.setThumbsUp (v.getThumbsUp ());
                    video.setThumbsDown (v.getThumbsDown ());
                    video.setVideoTitle (v.getVideoTitle ());
                    video.setVideoPath (v.getVideoPath ());
            try {
                return ResponseEntity
                        .ok ()
                        .location ( new URI ("/video/" + video.getId ()) )
                        .body (video);
            } catch (URISyntaxException e) {
                return ResponseEntity.status ( HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR ).build ();
            }
        }) .orElse ( ResponseEntity.notFound ().build ());
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id) throws Exception {
        Optional<Video> currentVideo = service.findVideo(id);
        if (currentVideo.isPresent()) {
            if (
            service.deleteFile(currentVideo.get().getInitialTitle())
                    .sdkHttpResponse().isSuccessful()) {
                service.delete(id);
            }
        }
        return true;
    }

    // Uploads the video to the aws bucket.
    @PostMapping("upload")
    public ResponseEntity<Video> uploadVideo(@RequestParam String videoName, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        System.out.println(videoName);
        Video tempVideo = service.saveVideo(videoName, multipartFile);
        if(tempVideo != null){
            return new ResponseEntity<>(tempVideo, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping("comment/{id}")
    public ResponseEntity<?> commentOnVideo(@PathVariable Long id, @RequestBody Comment commentText) {
        Optional<Video> tempVideo = service.findVideo(id);
        if (tempVideo.isPresent()) {
            service.commentOnVideo(commentText, id);
            return new ResponseEntity<>(tempVideo, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
    }
}
