package com.example.room.Model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username, email, image, joinDate;
    private ArrayList<String> idNovelasSubidas;
    private ArrayList<String> idNovelasLikes;


    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public ArrayList<String> getIdNovelasSubidas() {
        return idNovelasSubidas;
    }

    public void setIdNovelasSubidas(ArrayList<String> idNovelasSubidas) {
        this.idNovelasSubidas = idNovelasSubidas;
    }

    public ArrayList<String> getIdNovelasLikes() {
        return idNovelasLikes;
    }

    public void setIdNovelasLikes(ArrayList<String> idNovelasLikes) {
        this.idNovelasLikes = idNovelasLikes;
    }
}


