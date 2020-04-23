package com.videolibrary.zipcode.fullstackapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentId;

    @NotEmpty(message = "Comment message cannot be empty!")
    @Column(name="message")
    private String message;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id")
    private Video video;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private Date dateCreated;

    public Comment() { }

    public Comment(Long commentID, Video video, User user , String message){
        this.commentId = commentID;
        this.video = video;
        this.user = user;
        this.message = message;
    }

    public Comment(Video video, User user , String message){
        this.video = video;
        this.user = user;
        this.message = message;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
