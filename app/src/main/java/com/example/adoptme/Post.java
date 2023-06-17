package com.example.adoptme;

import java.util.ArrayList;


// Κλάση που προσομιώνει τα posts
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

    // Συνάρτηση που επιστρέφει τα πεδία του Post μέσα σε ArrayList<String>
    public ArrayList<String> toStringArraylist() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(Integer.toString(postId));
        arrayList.add(petName);
        arrayList.add(Integer.toString(age));
        arrayList.add(species);
        arrayList.add(postDescription);
        arrayList.add(phoneNumber);
        arrayList.add(townName);
        arrayList.add(Integer.toString(userId));
        return arrayList;
    }

}
