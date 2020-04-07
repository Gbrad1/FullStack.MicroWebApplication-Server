package com.videolibrary.zipcode.fullstackapp.Controllers;

import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import com.fasterxml.jackson.databind.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * The idea of a controller test is to have the mockMvc create a mock request. We are testing to make
 * sure the controller is reaching out to our mack service and returning the correct information.
 * */


@ExtendWith(SpringExtension.class) // Telling Junit to use it's spring extensions to talk to Spring.
@SpringBootTest // This tells spring to load. When the test runs, it hits this annotation and makes the environment.
@AutoConfigureMockMvc // This is what brings in Mockito
public class TestUserController {

    /*
     * This mockMvc is going to act as our client. You can think of it as a webpage.
     * This will pretend to do a web request. This mockMvc will talk to the controller.
     * */
    @Autowired
    private MockMvc mockMvc;

    /*
    * This is used to represent an object or mock an object.
    * We will make a mock object that stands in the place of service.
    **/
    @MockBean
    private UserService mockUserService;

    @Test
    @DisplayName("GET /User/1 - Found") // This will be the name of the test.
    public void testGetUserById() throws Exception {
        // Setup the mocked service
        User mockUser = new User(1L, "Winston", "The Corgi");
        doReturn(Optional.of(mockUser)).when(mockUserService).show(1L);

        // Execute the GET request
        mockMvc.perform(get("/User/{id}", 1))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1L)))
                .andExpect(jsonPath("$.firstName", is("Winston")))
                .andExpect(jsonPath("$.lastName", is("The Corgi")));

    }

}
