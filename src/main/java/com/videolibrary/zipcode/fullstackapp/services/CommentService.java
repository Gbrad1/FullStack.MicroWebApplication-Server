package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final VideoService videoService;

    @Autowired
    public CommentService(CommentRepository commentRepo, VideoService videoService){
        this.commentRepo = commentRepo;
        this.videoService = videoService;
    }

    public Optional<Comment> showComment(Long id){
        return commentRepo.findById(id);
    }

    public Iterable<Comment> showAll(){
        return commentRepo.findAll();
    }

    public Comment create(Long videoId, Comment comment) throws Exception {
        Optional<Video> foundVideo = videoService.findVideo(videoId);
        if(foundVideo.isPresent()){
            Video video = foundVideo.get();
            comment.setVideo(video);
            video.getComments().add(comment);
            Comment test = commentRepo.save(comment);
            videoService.basicSaveVideo(video);
            return test;
        } else throw new Exception("Not found");
    }


    public Boolean deleteComment(Long commentId){
        Comment comment = commentRepo.getOne(commentId);
        if(commentId.equals(comment.getCommentId())){
            commentRepo.deleteById(commentId);
            return true;
        }else{
            return false;
        }
    }

    public List<String> findByVideoId(Long videoId){
        List<String> comments = new ArrayList();
        commentRepo.findByVideoId(videoId);
        for(Comment comment : commentRepo.findAll()){
            if(comment.getVideo().equals(videoId)){
                comments.add(comment.getMessage());
            }
        }
        return comments;
    }

    public Comment updateCommentMessage(Long commentId, String newMessage) throws Exception {
        Optional<Comment> foundComment = commentRepo.findById(commentId);
        if(foundComment.isPresent()){
            foundComment.get().setMessage(newMessage);
            return commentRepo.save(foundComment.get());
        } else throw new Exception("Not found!");
    }
}
