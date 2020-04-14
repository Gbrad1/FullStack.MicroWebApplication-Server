package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Comment;
import com.videolibrary.zipcode.fullstackapp.repositories.CommentRepository;
import org.junit.Test;
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

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test findByIdSuccess")
    public void findByIdSuccessTest() {

        Comment mockComment = new Comment("This video is amazing!!");
        doReturn (Optional.of( mockComment )).when ( commentRepository ).findById(1L);

        Optional<Comment> testComment = commentRepository.findById( 1L );

        Assertions.assertTrue(testComment.isPresent(), "No Comment was found");
        Assertions.assertSame(testComment.get(), mockComment, "Comments don't match up");
    }

    @Test
    @DisplayName("Test findByIdFail")
    public void findByIdFailTest() {
        //Sets up mock repository
        doReturn ( Optional.empty () ).when ( commentRepository ).findById ( 1L );
        //Makes the service call
        Optional<Comment> testComment = commentRepository.findById ( 1L );
        //Checks to see if car is not found
        Assertions.assertFalse ( testComment.isPresent (), "Comment was found when it shouldn't have been" );
    }

    @Test
    @DisplayName ( "Test findAll" )
    public void testIndex() {
        //set up mock car list and repo
        Comment mockComment1 = new Comment("First");
        Comment mockComment2 = new Comment("No I'm first");
        Comment mockComment3 = new Comment("Oppa <3");
        Comment mockComment4 = new Comment("HAVE MY BABIES");

        doReturn ( Arrays.asList (mockComment1, mockComment2, mockComment3, mockComment4)).when(commentRepository).findAll();

        //Make the call to videoService
        List<Comment> mockCommentList = commentService.index();

        //Check assertions
        Assertions.assertEquals ( 4, mockCommentList.size (), "method should return 4 videos" );
    }

    @Test
    @DisplayName ( "Test saveVideo" )
    public void testCreateVideo() {
        Comment mockComment = new Comment ("If you click dislike you must be blind");
        doReturn ( mockComment ).when ( commentRepository ).save ( mockComment );

        Comment testComment = commentService.create ( mockComment );

        Assertions.assertNotNull ( testComment, "The comment we saved should not return Null" );
    }
}
