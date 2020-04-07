package com.videolibrary.zipcode.fullstackapp.models;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;


@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    @Nationalized
    private String content;

    @Lob
    private byte[] details;

    public Video() {}

    public Video(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getDetails() {
        return details;
    }

    public void setDetails(byte[] details) {
        this.details = details;
    }
}
