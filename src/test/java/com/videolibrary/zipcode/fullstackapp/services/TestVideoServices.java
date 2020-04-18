package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.repositories.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class TestVideoServices {

    @Autowired
    private VideoService videoService;

    @MockBean
    private VideoRepository videoRepository;

    @Test
    @DisplayName("Test findByIdSuccess")
    public void testGetVideoByIdSuccess() {
        Video mockVideo = new Video ( 1L, 4, 1,"testVideo", "urlPath" );
        doReturn ( Optional.of (mockVideo )).when ( videoRepository ).findById ( 1L ) ;

        Optional<Video> testVideo = videoService.findVideo ( 1L );

        Assertions.assertTrue(testVideo.isPresent(), "No Video was found when there should be one");
        Assertions.assertSame(testVideo.get(),mockVideo, "Models don't match up");
    }

    @Test
    @DisplayName ( "Test findByIdFail" )
    public void testGetVideoByIdFail() {
        //Sets up mock repository
        doReturn ( Optional.empty () ).when ( videoRepository ).findById ( 1L );
        //Makes the service call
        Optional<Video> testVideo = videoService.findVideo ( 1L );
        //Checks to see if car is not found
        Assertions.assertFalse ( testVideo.isPresent (), "Video was found when it shouldn't have been" );
        }

        @Test
        @DisplayName ( "Test findAll" )
        public void testIndex() {
            //set up mock car list and repo
//<<<<<<< HEAD:src/test/java/com/videolibrary/zipcode/fullstackapp/services/TestVideoServices.java
            Video mockVideo1 = new Video ( 1L, 3,1,"testVideo1", "urlPath1" );
            Video mockVideo2 = new Video ( 2L, 4, 1, "testVideo2", "urlPath2" );
            Video mockVideo3 = new Video ( 3L, 1, 3,"testVideo3", "urlPath3" );
            Video mockVideo4 = new Video ( 4L, 2, 2, "testVideo4", "urlPath4" );
//=======
//            Video mockVideo1 = new Video ( 1L, "testVideo1", "urlPath1", 0, 0 );
//            Video mockVideo2 = new Video ( 2L, "testVideo2", "urlPath2", 0, 0 );
//            Video mockVideo3 = new Video ( 3L, "testVideo3", "urlPath3", 0, 0 );
//            Video mockVideo4 = new Video ( 4L, "testVideo4", "urlPath4", 0, 0 );
//>>>>>>> d0099b703035b8276240bf7ee5f986f55ee280c4:src/test/java/com/videolibrary/zipcode/fullstackapp/Services/TestVideoServices.java
            doReturn ( Arrays.asList (mockVideo1, mockVideo2, mockVideo3, mockVideo4)).when(videoRepository).findAll ();

            //Make the call to videoService
            List<Video> mockVideoList = videoService.findAllVideos ();

            //Check assertions
            Assertions.assertEquals ( 4, mockVideoList.size (), "method should return 4 videos" );
        }

        @Test
        @DisplayName ( "Test saveVideo" )
        public void testCreateVideo() {
        // Set up mock video
//<<<<<<< HEAD:src/test/java/com/videolibrary/zipcode/fullstackapp/services/TestVideoServices.java
        Video mockVideo = new Video (1L, 4, 4, "testVideo1", "urlPath");
//=======
//        Video mockVideo = new Video (1L, "testVideo1", "urlPath", 0, 0);
//>>>>>>> d0099b703035b8276240bf7ee5f986f55ee280c4:src/test/java/com/videolibrary/zipcode/fullstackapp/Services/TestVideoServices.java
        doReturn ( mockVideo ).when ( videoRepository ).save ( mockVideo );

        // Make call to videoService
        Video testVideo = videoService.create ( mockVideo );

        //Confirm creation of video
        Assertions.assertNotNull ( testVideo, "The video we saved should not return Null" );
        }
    }









