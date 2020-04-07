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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /User/1 - Found")
    public void testGetUserById() throws Exception {
        // Setup the mocked service
        User mockUser = new User(1L, "Winston", "The Corgi");
        mockUserService.create(mockUser);
        String expectedOutcome = "{id: 1, firstName: Winston, lastName: The Corgi}";

        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders
                .get("/Users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedOutcome)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedOutcome));

    }

}
