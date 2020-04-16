package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.Video;
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
@RequestMapping("/Video/")
public class VideoController {

    private VideoService service;

    @Autowired
    public VideoController(VideoService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(service.index(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return this.service.findById ( id )
                .map ( video -> ResponseEntity
                .ok ()
                .body ( video ))
                .orElse ( ResponseEntity
                .notFound ()
                .build ());
    }

    @PostMapping("create")
    public ResponseEntity<Video> create(@RequestBody Video v) {
        Video video = service.create ( v );

            try {
                return ResponseEntity
                        .created ( new URI ( "/Video/" + video.getId () ) )
                        .body ( video );
            } catch (URISyntaxException e) {
                return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR ).build();
            }
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Video v) {
        Optional<Video> currentVideo = service.findById (id);

        return currentVideo
                .map(video -> {
                    video.setThumbsUp (v.getThumbsUp ());
                    video.setThumbsDown (v.getThumbsDown ());
                    video.setVideoTitle (v.getVideoTitle ());
                    video.setVideoPath (v.getVideoPath ());

            try {
                return ResponseEntity
                        .ok ()
                        .location ( new URI ("/Video/" + video.getId ()) )
                        .body (video);
            } catch (URISyntaxException e) {
                return ResponseEntity.status ( HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR ).build ();
            }
        }) .orElse ( ResponseEntity.notFound ().build ());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Video> currentVideo = service.findById (id);
        return  currentVideo
        .map(video -> {
            try {
                service.delete ( video.getId () );
            } catch (Exception e) {
                e.printStackTrace ();
            }
            return ResponseEntity.ok ().build ();
        })
          .orElse ( (ResponseEntity.notFound ().build ()) );
    }

    @PostMapping("upload")
    public ResponseEntity<Video> uploadVideo(@RequestParam String videoName, @RequestPart(value = "file") MultipartFile multipartFile) throws Exception {
        Video tempVideo = service.saveVideo(videoName, multipartFile);
        if(tempVideo != null){
            return new ResponseEntity<>(tempVideo, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
