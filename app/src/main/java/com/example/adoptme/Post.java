package com.example.adoptme;

import java.util.ArrayList;

public class Post {
    private int postId;
    private String townName;
    private String species;
    private String petName;
    private int age;
    private String phoneNumber;
    private ArrayList<String> imagePaths;

    public Post(int postId, String townName, String species, String petName, int age, String phoneNumber) {
        this.postId = postId;
        this.townName = townName;
        this.species = species;
        this.petName = petName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public int getPostId() {
        return postId;
    }

    public String getTownName() {
        return townName;
    }

    public String getSpecies() {
        return species;
    }

    public String getPetName() {
        return petName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }
}
