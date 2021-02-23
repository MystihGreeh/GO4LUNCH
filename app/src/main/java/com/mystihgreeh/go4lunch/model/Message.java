package com.mystihgreeh.go4lunch.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {
    private String message;
    private Date dateCreated;
    private Workmate workmateSender;
    private String urlImage;

    public Message() { }

    public Message(String message, Workmate workmateSender) {
        this.message = message;
        this.workmateSender = workmateSender;
    }

    public Message(String message, String urlImage, Workmate workmateSender) {
        this.message = message;
        this.urlImage = urlImage;
        this.workmateSender = workmateSender;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }
    public Workmate getWorkmateSender() { return workmateSender; }
    public String getUrlImage() { return urlImage; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(Workmate userSender) { this.workmateSender = userSender; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
}
