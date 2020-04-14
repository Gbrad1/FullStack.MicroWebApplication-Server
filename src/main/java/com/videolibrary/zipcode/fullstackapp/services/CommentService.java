package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public CommentService(CommentRepository userRepository) {
        this.repository = userRepository;
    }

    public Comment create(Comment comment) {
        return repository.save(comment);
    }

    public Comment show(Long id) {
        return repository.getCommentById(id);
    }

    public List<Comment> index() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }
}
