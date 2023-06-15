package com.example.adoptme;

public class Post {
    private int postId;
    private String townName;
    private String species;
    private String petName;
    private int age;
    private int userId;
    private String postDescription;
    private String phoneNumber;

    public Post(int postId, String townName, String species, String petName, int age, int userId,
                String postDescription, String phoneNumber) {
        this.postId = postId;
        this.townName = townName;
        this.species = species;
        this.petName = petName;
        this.age = age;
        this.userId = userId;
        this.postDescription = postDescription;
        this.phoneNumber = phoneNumber;
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

}
