package com.videolibrary.zipcode.fullstackapp.models;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestVideo {

    Video testVideo;

    @Test
    public void testNullaryConstructor() {
        testVideo = new Video ();
        Assert.assertNull ( testVideo.getId () );
        Assert.assertNull ( testVideo.getThumbsUp () );
        Assert.assertNull ( testVideo.getThumbsDown () );
        Assert.assertNull ( testVideo.getVideoTitle () );
        Assert.assertNull ( testVideo.getVideoTitle () );
    }

    @Test
    public void testConstructor1() {
        testVideo = new Video ( "Path to Fame", "urlPathToFame" );
        String actualVideoTitle = testVideo.getVideoTitle ();
        String actualVideoPath = testVideo.getVideoPath ();
        Assert.assertNull ( testVideo.getId () );
        Assert.assertEquals ( "Path to Fame", actualVideoTitle );
        Assert.assertEquals ( "urlPathToFame", actualVideoPath );
    }

    @Test
    public void testConstructor2() {
        testVideo = new Video ( 1L, 4, 2,"Path to Glory", "urlPathToGlory" );
        Long expected = 1L;
        Integer expectedThumbsUp = 4;
        Integer expectedThumbsDown = 2;
        Assert.assertEquals ( expected, testVideo.getId () );
        Assert.assertEquals ( expectedThumbsUp, testVideo.getThumbsUp () );
        Assert.assertEquals ( expectedThumbsDown, testVideo.getThumbsDown () );
        Assert.assertEquals ( "Path to Glory", testVideo.getVideoTitle () );
        Assert.assertEquals ( "urlPathToGlory", testVideo.getVideoPath () );
    }

    @Test
    public void testSetId() {
        testVideo = new Video ( 4L, 5, 1,"Monty Python and the Holy Grail", "urlMPatHG" );
        testVideo.setId ( 3L );
        Long expected = 3L;
        Assert.assertEquals ( expected, testVideo.getId () );
    }

    @Test
    public void testSetThumbsUpAndDown() {
        testVideo = new Video ( 4L, 5, 1,"Monty Python and the Holy Grail", "urlMPatHG" );
        testVideo.setThumbsUp ( 4 );
        testVideo.setThumbsDown ( 2 );
        Integer expectedThumbsUp = 4;
        Integer expectedThumbsDown = 2;
        Assert.assertEquals ( expectedThumbsUp, testVideo.getThumbsUp () );
        Assert.assertEquals ( expectedThumbsDown, testVideo.getThumbsDown () );
    }

    @Test
    public void testSetMovieTitleAndPath() {
        testVideo = new Video ( 4L, 5, 1,"Monty Python and the Holy Grail", "urlMPatHG" );
        testVideo.setVideoTitle ( "Monty Python's Meaning of Life" );
        testVideo.setVideoPath ( "urlMPMoL" );
        String expectedVideoTitle = "Monty Python's Meaning of Life";
        String expectedVideoPath = "urlMPMoL";
        Assert.assertEquals(expectedVideoTitle, testVideo.getVideoTitle ());
        Assert.assertEquals(expectedVideoPath, testVideo.getVideoPath ());
    }
}


