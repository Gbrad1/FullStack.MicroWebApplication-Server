package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.repositories.VideoRepository;
import org.junit.Assert;
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
import java.util.Date;
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
            Video mockVideo1 = new Video ( 1L, 3,1,"testVideo1", "urlPath1" );
            Video mockVideo2 = new Video ( 2L, 4, 1, "testVideo2", "urlPath2" );
            Video mockVideo3 = new Video ( 3L, 1, 3,"testVideo3", "urlPath3" );
            Video mockVideo4 = new Video ( 4L, 2, 2, "testVideo4", "urlPath4" );
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
        Video mockVideo = new Video (1L, 4, 4, "testVideo1", "urlPath");
        doReturn ( mockVideo ).when ( videoRepository ).save ( mockVideo );

        // Make call to videoService
        Video testVideo = videoService.create ( mockVideo );

        //Confirm creation of video
        Assertions.assertNotNull ( testVideo, "The video we saved should not return Null" );
        }

        @Test
        @DisplayName ( "Test DeleteVideo" )
        public void testDeleteVideo() throws Exception {
            // Set up mock video and save
            Video mockVideo = new Video (1L, 5, 2, "testVideo1", "urlPath");
            doReturn ( mockVideo ).when ( videoRepository ).save ( mockVideo );
            Video testVideo = videoService.create ( mockVideo );

            //Make call to videoService to confirm deletion of video
            Assertions.assertTrue ( videoService.delete(1L));
        }


    @Test
    public void testGenerateFileName() {
        Long dateTime = new Date ().getTime();
        Long expDateTime = dateTime + 1;

        String fileName = "Mutiny on the Bounty";
        String expected = dateTime + "-Mutiny_on_the_Bounty";
        System.out.println (expected);

        String actual = videoService.generateFileName (fileName);
        System.out.println (actual);

        Assert.assertEquals(expected, actual);


    }
    }









