package com.videolibrary.zipcode.fullstackapp.Services;

import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.repositories.UserRepository;
import com.videolibrary.zipcode.fullstackapp.services.UserService;
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
public class TestUserService {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test findByIdSuccess")
    public void testFindByIdSuccess() {
        // Create a mock POJO
        User mockUser = new User(1L, "Winston", "Corgington");
        doReturn(Optional.of(mockUser)).when(userRepository).findById(1L);

        // Execute the call
        Optional<User> returnUser = userService.findById(1L);

        Assertions.assertTrue(returnUser.isPresent(), "No User was found.");
        Assertions.assertSame(returnUser.get(), mockUser, "User's don't match");
    }

    @Test
    @DisplayName("Test findByIdFail")
    public void TestFindByIdFail() {
        // Setup mock repository
        doReturn(Optional.empty()).when(userRepository).findById(1L);

        // Execute the service call
        Optional<User> returnUser = userRepository.findById(1L);

        // Check assertions
        Assertions.assertFalse(returnUser.isPresent(), "User was found when it shouldn't be");
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAllUsers() {
        // Setup as many users as you would like.
        User mockUserWinston = new User(1L, "Winston", "Corgington");
        User mockUserArchie = new User(2L, "Archie", "Bradford");
        User mockUserSassy = new User(3L, "Sassy", "Defilippis");
        doReturn(Arrays.asList(mockUserWinston, mockUserSassy, mockUserArchie)).when(userRepository).findAll();

        // Execute the service call
        List<User> listOfUsers = userService.findAll();

        // Check assertions
        Assertions.assertEquals(3, listOfUsers.size(), "Find all method should return 3 users.");
    }

    @Test
    @DisplayName("Test add a user")
    public void testAddUser() {
        User mockUser = new User(1L, "Rina", "Bradford");
        doReturn(mockUser).when(userRepository).save(mockUser);

        User returnUser = userService.create(mockUser);

        Assertions.assertNotNull(returnUser, "The saved user should not be null.");

    }

    @Test
    @DisplayName("Test delete a user.")
    public void testDeleteUserSuccess() {
        User mockUser = new User(1L, "Rina", "Bradford");
        doReturn(mockUser).when(userRepository).getOne(1L);

        boolean result = userService.delete(1L);

        Assertions.assertFalse(result, "The user was not deleted");
    }

    @Test
    @DisplayName("Test delete a user fail.")
    public void testDeleteUserFail() {
        User mockUser = new User(1L, "Rina", "Bradford");
        doReturn(mockUser).when(userRepository).getOne(1L);

        boolean result = userService.delete(2L);

        Assertions.assertFalse(result, "User was deleted.");
    }

    @Test
    @DisplayName("Test retrieving updated information.")
    public void testUpdateUser() {
        // setup mock object and repository
        User mockUser = new User(1L, "Archie", "Bradford");
        User newUser = new User(2L, "Sassy", "Defilippis");

        // This doReturn method is asking for what we want back when the object is found.
        doReturn(mockUser).when(userRepository).getOne(1L);

        // execute the call
        User user = userService.update(1L, newUser);

        Assertions.assertEquals(user.getFirstName(), "Sassy", "The user's first name was not updated.");
        Assertions.assertEquals(user.getLastName(), "Defilippis", "The user's last name was not updated.");
    }

    @Test
    @DisplayName("Test to update the firstName of a user.")
    public void testUpdateFirstName() {
        User mockUser = new User(1L, "Archie", "Bradford");
        String newFirstName = "Rina";

        doReturn(mockUser).when(userRepository).getOne(1L);

        User user = userService.updateFirstName(1L, newFirstName);

        Assertions.assertEquals(user.getFirstName(), "Rina", "The user's first name was not updated.");
    }

    @Test
    @DisplayName("Test to update the lastname of a user.")
    public void testUpdateLastName() {
        User mockUser = new User(1L, "Rue", "Bradford");
        String newLastName = "Giles";

        doReturn(mockUser).when(userRepository).getOne(1L);

        User user = userService.updateLastName(1L, newLastName);
        Assertions.assertEquals(user.getLastName(), "Giles", "The user'slast name was not updated.");
    }
}
