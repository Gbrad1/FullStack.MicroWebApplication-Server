package com.videolibrary.zipcode.fullstackapp.Models;

import com.videolibrary.zipcode.fullstackapp.models.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestUser {

    User user;

    @Test
    public void testNullaryConstructUser() {
        user = new User();
        Assert.assertNull(user.getUser_Id());
        Assert.assertNull(user.getFirstName());
        Assert.assertNull(user.getLastName());
    }

    @Test
    public void testNonNullaryConstructorUser() {
        user = new User(2L, "Winston", "Corgington");
        Long expectedId = 2L;
        Assert.assertEquals(expectedId, user.getUser_Id());
        Assert.assertEquals("Winston", user.getFirstName());
        Assert.assertEquals("Corgington", user.getLastName());
    }

    @Test
    public void testSetId() {
        user = new User(2L, "Winston", "Corgington");
        user.setUser_Id(4L);
        Long expectedId = 4L;
        Assert.assertEquals(expectedId, user.getUser_Id());
        Assert.assertEquals("Winston", user.getFirstName());
        Assert.assertEquals("Corgington", user.getLastName());
    }

    @Test
    public void testSetters() {
        user = new User();
        user.setUser_Id(1L);
        user.setFirstName("Winston");
        user.setLastName("Corgington");

        Assert.assertEquals("Winston", user.getFirstName());
        Assert.assertEquals("Corgington", user.getLastName());
    }
}
