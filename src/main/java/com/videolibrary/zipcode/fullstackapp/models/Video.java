package com.videolibrary.zipcode.fullstackapp.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="thumbs_up")
    private Integer thumbsUp;
    @Column(name="thumbs_down")
    private Integer thumbsDown;
    @Column(name="video_title")
    private String videoTitle;
    @Column(name="video_path")
    private String videoPath;

    @OneToMany
    private List<Comment> comments;

    public Video() {}

    public Video(String videoTitle, String videoPath) {
        this.thumbsUp = 0;
        this.thumbsDown = 0;
        this.videoTitle = videoTitle;
        this.videoPath = videoPath;
    }

    public Video(Long id, String videoTitle, String videoPath) {
        this.id = id;
        this.thumbsUp = 0;
        this.thumbsDown = 0;
        this.videoTitle = videoTitle;
        this.videoPath = videoPath;
        this.comments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
