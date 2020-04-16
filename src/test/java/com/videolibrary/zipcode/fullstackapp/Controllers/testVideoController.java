package com.videolibrary.zipcode.fullstackapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videolibrary.zipcode.fullstackapp.models.Video;
import com.videolibrary.zipcode.fullstackapp.services.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestVideoController {

    @MockBean
    private VideoService mockVideoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName ( "GET /Video/1 - Found" )
    public void testGetVideoById() throws Exception {
        //setup Mock Service
        Video mockVideo = new Video ( 1L, "TestVideo1", "urlPath");
        doReturn(mockVideo).when(mockVideoService).create(mockVideo);
        doReturn(Optional.of(mockVideo)).when(mockVideoService).findById (1L);

        //execute GET request
        mockMvc.perform ( get ( "/Video/{id}", 1 ) )
                .andExpect ( status ().isOk () )
                .andExpect ( content ().contentType ( MediaType.APPLICATION_JSON ) )
                .andExpect ( jsonPath ( "$.id", is ( 1 ) ) )
                .andExpect ( jsonPath ( "$.thumbsUp", is ( 0 ) ) )
                .andExpect ( jsonPath ( "$.thumbsDown", is(0 ) ))
                .andExpect ( jsonPath ( "$.videoTitle", is ( "TestVideo1" ) ) )
                .andExpect ( jsonPath ( "$.videoPath", is("urlPath")  ));
        }

    @Test
    @DisplayName ( "GET /Video/1 - Not Found" )
   public void testVideoFoundById() throws Exception {
        //Establish mocked service
        doReturn ( Optional.empty () ).when ( mockVideoService ).findById ( 1L );

        //Perform the GET request
        mockMvc.perform ( get ("/Video/{id}",2) )

        //Confirm the car is not there
                .andExpect ( status ().isNotFound ());
    }

    @Test
    @DisplayName ( "POST /Video - Success" )
    public void testCreateVideo() throws Exception {
            //Set up mock video
        Video postVideo = new Video("testVideo", "urlPath");
        Video mockVideo = new Video ( 1L, "testVideo", "urlPath" );
        doReturn ( mockVideo ).when ( mockVideoService ).create ( any() );
            // given(mockVideoService.create ( postVideo )).willReturn(mockVideo);
        mockMvc.perform ( post ( "/Video/create" )
                //.accept ( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON)
                .content(asJsonString( postVideo ) ))

                .andExpect(status ().isCreated ())
                .andExpect(content ().contentType ( MediaType.APPLICATION_JSON_VALUE ))

                .andExpect(header ().string( HttpHeaders.LOCATION, "/Video/1" ))

                .andExpect ( jsonPath ( "$.id", is ( 1 ) ) )
                .andExpect ( jsonPath ( "$.thumbsUp", is ( 0 ) ) )
                .andExpect ( jsonPath ( "$.thumbsDown", is(0 ) ))
                .andExpect ( jsonPath ( "$.videoTitle", is ( "testVideo" ) ) )
                .andExpect ( jsonPath ( "$.videoPath", is("urlPath")  ));
    }

    @Test
    @DisplayName ( "PUT /Video/1" )
    public void testUpdateVideoPass() throws Exception {
        //Create mock video
        Video putVideo = new Video("TestVideo3", "urlPath3");
        Video mockVideo = new Video( 3L, "TestVideo3", "urlPath3");
        doReturn ( Optional.of(mockVideo) ).when ( mockVideoService ).findById (3L);
        doReturn ( mockVideo ).when ( mockVideoService ).update ( 3L );

        mockMvc.perform ( put("/Video/update/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .header ( HttpHeaders.IF_MATCH, 3 )
                .content(asJsonString (putVideo) ))

                .andExpect(status ().isOk ())
                .andExpect(content ().contentType ( MediaType.APPLICATION_JSON_VALUE ))

                .andExpect(header ().string ( HttpHeaders.LOCATION, "/Video/3" ))

                .andExpect ( jsonPath ( "$.id", is ( 3 ) ) )
                .andExpect ( jsonPath ( "$.thumbsUp", is ( 0 ) ) )
                .andExpect ( jsonPath ( "$.thumbsDown", is(0 ) ))
                .andExpect ( jsonPath ( "$.videoTitle", is ( "TestVideo3" ) ) )
                .andExpect ( jsonPath ( "$.videoPath", is("urlPath3")  ));
    }

    @Test
    @DisplayName ( ("PUT /Video/1 - Not Found") )
    public void testUpdateVideoFail() throws Exception {
        //Create mock Video
        Video putVideo = new Video ("testFailVideo", "urlPath4");
        doReturn ( Optional.empty () ).when ( mockVideoService ).findById (3L);

        mockMvc.perform ( put("/Video/update/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 3)
                .content(asJsonString ( putVideo )))

                .andExpect(status ().isNotFound ());
    }



    @Test
    @DisplayName ( ("DELETE /Video/1 - Success") )
    public void deleteVideo() throws Exception {
        //Create mock video
        Video mockVideo = new Video ( 1L, "TestVideo1", "urlPath");

        //Establish mocked Service
        doReturn ( Optional.of ( mockVideo ) ).when ( mockVideoService ).findById ( 1L );
        doReturn ( true ).when ( mockVideoService ).delete ( 1L );

        //Execute the delete request
        mockMvc.perform ( delete ( "/Video/{id}", 1 ) )
                .andExpect ( status ().isOk () );
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper ().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
