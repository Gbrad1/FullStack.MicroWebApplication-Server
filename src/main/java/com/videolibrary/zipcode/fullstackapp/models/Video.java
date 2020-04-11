package com.videolibrary.zipcode.fullstackapp.models;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;


@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String videoTitle;
    private String videoPath;

    public Video() {}

    public Video(String title, String videoPath) {
        this.videoTitle = title;
        this.videoPath = videoPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return videoTitle;
    }

    public void setTitle(String title) {
        this.videoTitle = title;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
