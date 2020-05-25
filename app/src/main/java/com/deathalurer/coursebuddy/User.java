package com.deathalurer.coursebuddy;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class User {
    private String Username,phoneNumber,UserUniqueId,email,bio,college,profileImage;
    private ArrayList<DocumentReference> courseCompleted,courseEnrolled;

    public User(String username, String phoneNumber, String userUniqueId, String email, String userBio, String userCollege, ArrayList<DocumentReference> courseCompleted, ArrayList<DocumentReference> courseEnrolled,String profileImage) {
        Username = username;
        this.phoneNumber = phoneNumber;
        UserUniqueId = userUniqueId;
        this.email = email;
        this.bio = userBio;
        this.college = userCollege;
        this.courseCompleted = courseCompleted;
        this.courseEnrolled = courseEnrolled;
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserBio() {
        return bio;
    }

    public void setUserBio(String userBio) {
        this.bio = userBio;
    }

    public String getUserCollege() {
        return college;
    }

    public void setUserCollege(String userCollege) {
        this.college = userCollege;
    }

    public User() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserUniqueId() {
        return UserUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        UserUniqueId = userUniqueId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public ArrayList<DocumentReference> getCourseCompleted() {
        return courseCompleted;
    }

    public void setCourseCompleted(ArrayList<DocumentReference> courseCompleted) {
        this.courseCompleted = courseCompleted;
    }

    public ArrayList<DocumentReference> getCourseEnrolled() {
        return courseEnrolled;
    }

    public void setCourseEnrolled(ArrayList<DocumentReference> courseEnrolled) {
        this.courseEnrolled = courseEnrolled;
    }
}
