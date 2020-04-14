package com.videolibrary.zipcode.fullstackapp.repositories;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment getCommentById(Long id);
}
