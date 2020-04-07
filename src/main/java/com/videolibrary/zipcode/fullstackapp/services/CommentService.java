package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private CommentRepository repository;

    @Autowired
    public CommentService(CommentRepository userRepository) {
        this.repository = userRepository;
    }

    public Comment create(Comment comment) {
        return repository.save(comment);
    }

    public Comment show(Long id) {
        return repository.getCommentById(id);
    }

    public Iterable<Comment> index() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }
}
