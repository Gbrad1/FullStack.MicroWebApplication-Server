package com.videolibrary.zipcode.fullstackapp.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommentTest {
    Comment comment;

    @Before
    public void setUp() {
        comment = new Comment();
    }

    @After
    public void tearDown() {
        comment = null;
    }

    @Test
    public void instanceOfComment(){
        assertTrue(comment instanceof Comment);
    }

    @Test
    public void nullConstructor(){
        assertNull(comment.getMessage());
    }

    @Test
    public void setCommentID(){
        assertNull(comment.getCommentId());
        comment.setCommentId(Long.valueOf(1));
        assertEquals(Long.valueOf(1) , comment.getCommentId());
    }

    @Test
    public void setUserID(){
        assertNull(comment.getCommentId());
        comment.setUserId(Long.valueOf(1001));
        assertEquals(Long.valueOf(1001) , comment.getUserId());
    }

    @Test
    public void setMessage() {
        assertNull(comment.getCommentId());
        comment.setMessage("First Comment");
        assertEquals("First Comment", comment.getMessage());
    }

    @Test
    public void getMessage(){
        assertNull(comment.getVideo());
    }

    @Test
    public void setVideo(){
        Video video = new Video();
        comment.setVideo(video);
        assertNotNull(comment.getVideo());
    }

}
