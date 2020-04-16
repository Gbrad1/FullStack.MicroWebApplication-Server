package com.videolibrary.zipcode.fullstackapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Comment {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long commentId;
    private Long videoId;
    private Long userId;
    private String comment;
    @NotEmpty(message = "Comment message cannot be empty!")
    private String message;
    @JsonIgnore
    @ManyToOne
    private Video video;


    public Comment(){
        this.commentId = null;
        this.videoId = null;
        this.userId = null;
        this.comment = null;
        this.message = null;
    }

    public Comment(Long commentID, Long videoId , Long userId , String comment){
        this.commentId = commentID;
        this.videoId = videoId;
        this.userId = userId;
        this.comment = comment;
    }
    public Comment(Long videoId , Long userId , String comment){
        this.videoId = videoId;
        this.userId = userId;
        this.comment = comment;
    }

    public Comment(Long videoId , String comment){
        this.videoId = videoId;
        this.userId = Long.valueOf(0000);
        this.comment = comment;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
