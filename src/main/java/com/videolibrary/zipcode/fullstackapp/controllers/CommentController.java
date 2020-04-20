package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> showComment(@PathVariable Long id){
        Optional<Comment> commentOptional = this.service.showComment(id);
        ResponseEntity<Comment> response = commentOptional
                .map(comment -> ResponseEntity.ok().body(comment))
                .orElse(ResponseEntity.notFound().build());
        return response;
    }

    @GetMapping("/comments")
    public ResponseEntity<Iterable<Comment>> showAll(){
        return new ResponseEntity<>(service.showAll() , HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> create(@RequestBody Comment comment){

        Comment newComment = this.service.create(comment);
        try {
            return ResponseEntity
                    .created( new URI("/create" + newComment.getCommentId()))
                    .body(newComment);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping(value ="/comments/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long commentId) {
        return new ResponseEntity<>(service.deleteComment(commentId) , HttpStatus.OK);
    }

    @GetMapping("/comments/videos/{videoId}")
    public ResponseEntity<List<String>> findCommentsByVideoId(@PathVariable Long videoId){
        return new ResponseEntity<>(service.findByVideoId(videoId) , HttpStatus.OK);
    }
}
