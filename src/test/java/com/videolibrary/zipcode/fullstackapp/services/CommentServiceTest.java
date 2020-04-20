package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.repositories.CommentRepository;
import com.videolibrary.zipcode.fullstackapp.repositories.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private VideoService videoService;

    @MockBean
    private VideoRepository videoRepository;


    @Test
    @DisplayName("Test showComment Success")
    public void testFindByIdFound(){
        Video mockVideo = new Video("Test video", "url",0,0);
        User mockUser = new User(1L, "John", "Doe");
        Comment mockComment = new Comment(mockVideo, mockUser,"Test comment");
        doReturn(Optional.of(mockComment)).when(commentRepository).findById(1L);
        Optional<Comment> resultComment = commentService.showComment(1L);
        Assertions.assertTrue(resultComment.isPresent());
        Assertions.assertSame(resultComment.get(),mockComment);
    }


    @Test
    @DisplayName("Test showComment Failure")
    public void testFindByIdNotFound(){
        doReturn(Optional.empty()).when(commentRepository).findById(1L);
        Optional<Comment> resultComment = commentService.showComment(1L);
        Assertions.assertFalse(resultComment.isPresent());
    }


    @Test
    @DisplayName("Test showAll")
    public void testShowAll(){// Set up mock object and repository
        Video mockVideo1 = new Video("Test video 1", "url",0,0);
        User mockUser1 = new User(1L, "John", "Doe");
        Comment mockComment1 = new Comment(mockVideo1, mockUser1,"Test comment");
        Video mockVideo2 = new Video("Test video 2", "url",0,0);
        User mockUser2 = new User(1L, "Johnson", "Smith");
        Comment mockComment2 = new Comment(mockVideo2, mockUser2,"Test comment 2");
        doReturn(Arrays.asList(mockComment1,mockComment2)).when(commentRepository).findAll();
        List<Comment> commentsList = (List<Comment>) commentService.showAll();
        Assertions.assertEquals(2,commentsList.size());
    }


    @Test
    @DisplayName("Test create")
    public void testCreate() throws Exception {
        Video mockVideo = new Video ("TestVideo", "urlPath",0,0);
        User mockUser = new User(1L, "John", "Doe");
        Comment mockComment = new Comment(mockVideo, mockUser,"Test comment");
        doReturn(mockVideo).when(videoRepository).save(mockVideo);
        doReturn(Optional.of(mockVideo)).when(videoService).findVideo(1L);
        doReturn(mockComment).when(commentRepository).save(mockComment);
        Comment resultComment = commentService.create(mockComment);
        Assertions.assertNotNull(resultComment);
    }


    @Test
    @DisplayName("Test delete comment")
    public void testDeleteComment(){
        Video mockVideo = new Video ("TestVideo", "urlPath",0,0);
        User mockUser = new User(1L,"John", "Doe");
        Comment mockComment = new Comment(mockVideo, mockUser,"Test comment");
        doReturn(mockComment).when(commentRepository).save(mockComment);
        doReturn(mockComment).when(commentRepository).getOne(1L);
        Boolean deleted = commentService.deleteComment(1L);
        Assertions.assertTrue(deleted);
    }
}
