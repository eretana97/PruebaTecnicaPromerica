package com.eretana.entrevista.models;

import java.util.ArrayList;
import java.util.List;

public class XPost {

    private int id;
    private int userId;
    private String username;
    private int minutes;
    private String title;
    private String body;
    private List<Commnet> comments;

    public XPost() {
        comments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Commnet> getComments() {
        return comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setComments(List<Commnet> comments) {
        this.comments = comments;
    }
}
