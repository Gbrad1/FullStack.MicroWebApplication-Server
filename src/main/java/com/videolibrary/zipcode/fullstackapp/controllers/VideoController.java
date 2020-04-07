package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {

    private VideoService service;

    @Autowired
    public VideoController(VideoService service) {
        this.service = service;
    }

    @GetMapping("/Video/{id}")
    public ResponseEntity<Video> show(@PathVariable Long id) {
        return new ResponseEntity<>(service.show(id), HttpStatus.OK);
    }

    @PostMapping("/Video")
    public ResponseEntity<Video> create(@RequestBody Video v) {
        return new ResponseEntity<>(service.create(v), HttpStatus.CREATED);
    }

    @PutMapping("/Video/{id}")
    public ResponseEntity<Video> update(@PathVariable Long id, @RequestBody Video v) {
        return new ResponseEntity<>(service.update(id, v), HttpStatus.OK);
    }

    @DeleteMapping("/Video/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}
