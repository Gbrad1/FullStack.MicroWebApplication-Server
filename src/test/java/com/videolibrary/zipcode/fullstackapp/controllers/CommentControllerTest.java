package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test showById Success")
    public void testShowCommentFound() throws Exception{
        Video mockVideo = new Video(1L, 0, 0,"Test video", "url");
        User mockUser = new User(1L, "John", "Doe");
        Comment mockComment = new Comment(mockVideo, mockUser,"Test comment");
        Optional<Comment> commentOptional = Optional.of(mockComment);
        doReturn(commentOptional).when(commentService).showComment(1L);
        mockMvc.perform(get("/comments/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId",is(1)))
                .andExpect(jsonPath("$.user_id",is(1)))
                .andExpect(jsonPath("$.message",is("Test comment")));
    }

    @Test
    @DisplayName("Test showById Fail")
    public void testShowCommentNotFound() throws Exception {

        doReturn(Optional.empty()).when(commentService).showComment(1L);

        Optional<Comment> testComment = commentService.showComment(1L);

        mockMvc.perform(get("/comments/{id}", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test findAll")
    public void testShowAllComments() throws Exception{
        Video mockVideo1 = new Video(1L, 0, 0, "Test video 1", "url");
        User mockUser1 = new User(1L, "John", "Doe");
        Comment mockComment1 = new Comment(1L, mockVideo1, mockUser1,"Test comment");
        Video mockVideo2 = new Video(0,0,"Test video 2", "url");
        User mockUser2 = new User(2L, "Johnson", "Smith");
        Comment mockComment2 = new Comment(mockVideo2, mockUser2,"Test comment 2");

        Iterable<Comment> comments = new ArrayList<>(Arrays.asList(mockComment1,mockComment2));
        doReturn(comments).when(commentService).showAll();

        mockMvc.perform(get("/comments/",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.commentId",is(1)))
                .andExpect(jsonPath("$.user_id",is(1)))
                .andExpect(jsonPath("$.message",is("Test comment 1")))

                .andExpect(jsonPath("$[1].commentId",is(2)))
                .andExpect(jsonPath("$[1].user_id",is(2)))
                .andExpect(jsonPath("$[1].message",is("Test comment 2")));
    }

    @Test
    @DisplayName("Test create")
    public void testCreateComment() throws Exception{

        Video mockVideo = new Video(1L, 0, 0,"Test video", "url");

        User mockUser = new User(1L, "John", "Doe");
        Comment mockComment = new Comment(mockVideo, mockUser,"Test comment");

        given(commentService.create(mockVideo.getId(), mockComment)).willReturn(mockComment);
        mockMvc.perform(post("/comments/create")
                .contentType(MediaType.APPLICATION_JSON));
    }
}
