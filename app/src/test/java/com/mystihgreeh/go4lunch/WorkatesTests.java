package com.mystihgreeh.go4lunch;

import androidx.annotation.Nullable;

import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.List;

public class WorkatesTests extends TestCase {

    private Workmate workmate;
    private String uid = "uid";
    private String name = "name";
    @Nullable
    private String email = "email";
    @Nullable
    private String photoUrl = "photoUrl";
    @Nullable
    private List<String> likedRestaurants;

    @Before
    public void setup() {}

    public void testWorkmateId() {
        workmate = new Workmate(uid,name,email,photoUrl);
        assertEquals(uid, workmate.getUid());

        String setData = "uid";
        workmate.setUid(setData);

        String getData = workmate.getUid();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testWorkmateName() {
        workmate = new Workmate(uid,name,email,photoUrl);
        assertEquals(name, workmate.getUsername());

        String setData = "name";
        workmate.setUsername(setData);

        String getData = workmate.getUsername();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }


    public void testWorkmateUrlPicture() {
        workmate = new Workmate(uid,name, photoUrl, email);
        assertEquals(photoUrl, workmate.getUrlPicture());

        String setData = "photoUrl";
        workmate.setUrlPicture(setData);

        String getData = workmate.getUrlPicture();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testWorkmateEmail() {
        workmate = new Workmate(uid,name, photoUrl, email);
        assertEquals(email, workmate.getUseremail());

        String setData = "email";
        workmate.setUseremail(setData);

        String getData = workmate.getUseremail();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testLikedRestaurants() {
        workmate = new Workmate(name,uid, email,photoUrl);
        assertNull(workmate.getLikedRestaurants());

        String setData = "uid";
        workmate.setUseremail(setData);

        String getData = workmate.getUsername();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }
}
