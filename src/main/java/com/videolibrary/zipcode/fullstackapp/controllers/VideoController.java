package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Video> show(@PathVariable Long id) {
        return new ResponseEntity<>(service.show(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Video> create(@RequestBody Video v) {
        return new ResponseEntity<>(service.create(v), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Video> update(@PathVariable Long id, @RequestBody Video v) {
        return new ResponseEntity<>(service.update(id, v), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) throws Exception {
        return new ResponseEntity<>(service.delete(id), HttpStatus.GONE);
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
