package com.videolibrary.zipcode.fullstackapp.models;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer thumbsUp;
    private Integer thumbsDown;
    private String videoTitle;
    private String videoPath;

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
}
