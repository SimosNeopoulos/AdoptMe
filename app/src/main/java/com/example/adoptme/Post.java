package com.example.adoptme;

import java.util.ArrayList;

public class Post {
    private int postId;
    private String townName;
    private String species;
    private String petName;
    private int age;
    private int userId;
    private String postDescription;
    private String phoneNumber;
    private ArrayList<String> imagePaths;

    public Post(int postId, String townName, String species, String petName, int age, int userId,
                String postDescription, String phoneNumber, ArrayList<String> imagePaths) {
        this.postId = postId;
        this.townName = townName;
        this.species = species;
        this.petName = petName;
        this.age = age;
        this.userId = userId;
        this.postDescription = postDescription;
        this.phoneNumber = phoneNumber;
        this.imagePaths = imagePaths;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPostDescription() {
        return postDescription;
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
