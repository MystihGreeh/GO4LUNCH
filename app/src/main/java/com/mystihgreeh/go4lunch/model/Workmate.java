
package com.mystihgreeh.go4lunch.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Workmate implements Serializable {

    public static final List<Workmate> ITEMS = new ArrayList<>();

    private String uid;
    private String username;
    @Nullable private String urlPicture;
    private String useremail;

    public Workmate() { }

    public Workmate(String uid, String username, @org.jetbrains.annotations.Nullable String urlPicture, String useremail) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.useremail = useremail;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    @org.jetbrains.annotations.Nullable
    public String getUrlPicture() { return urlPicture; }
    public String getUseremail() { return useremail; }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(@org.jetbrains.annotations.Nullable String urlPicture) { this.urlPicture = urlPicture; }
    public void setIsMentor(String useremail) { this.useremail = useremail; }



}
