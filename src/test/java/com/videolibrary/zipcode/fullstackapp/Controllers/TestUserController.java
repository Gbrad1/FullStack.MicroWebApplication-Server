package com.videolibrary.zipcode.fullstackapp.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.services.UserService;
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
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
     * This is used to represent an object or mock an object.
     * We will make a mock object that stands in the place of service.
     **/
    @MockBean
    private UserService userService;

    /*
     * This mockMvc is going to act as our client. You can think of it as a webpage.
     * This will pretend to do a web request. This mockMvc will talk to the controller.
     * */
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /user/1 - Found") // This will be the name of the test.
    public void testGetUserById() throws Exception {
        // Setup the mocked service
        User mockUser = new User(1L, "Winston", "The Corgi");
        doReturn(Optional.of(mockUser)).when(userService).findById(1L);

        // Execute the GET request
        mockMvc.perform(get("/user/{id}", 1))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.user_Id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Winston")))
                .andExpect(jsonPath("$.lastName", is("The Corgi")));
    }

    @Test
    @DisplayName("CREATE /user/create - Created")
    public void testCreateUser() throws Exception {
        User mockUser = new User(1L, "Archie", "Bradford");
        /*
        * Same as the other doReturn() method but this is logically backwards. Given this behavior,
        * return to me this mockUser.
        * We use any() because we need to match whatever is being passed in with the content body.
        * */
        given(userService.create(any())).willReturn(mockUser);
        /*
        * We use the "asJsonString()" method on the content type to pass in the body of the JSON file in string format.
        * as we would in postman.
        **/
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockUser)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.user_Id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Archie")))
                .andExpect(jsonPath("$.lastName", is("Bradford")));
    }

    @Test
    @DisplayName("PUT /User/3 - Updated")
    public void testPutUser() throws Exception {
        User mockUser = new User(3L, "Wrong", "Name");
        User updatedMockUser = new User(3L, "Right", "Name");
        /*
        * Why do we use any()? The mock object that is being returned in the request body(int the controller) is not
        * the same as the one that we are mocking. The comparison uses the address which is different just like hashcoding.
        * We pass it any to bypass the comparison of hashcodes.
        * */
        given(userService.update(3L, mockUser)).willReturn(updatedMockUser);

        mockMvc.perform(put("/user/update/{id}", 3).contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.user_Id", is(3)))
                .andExpect(jsonPath("$.firstName", is("Right")))
                .andExpect(jsonPath("$.lastName", is("Name")));
    }


    // This method returns the JSON file in a string format.
    static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
