package com.deathalurer.coursebuddy;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class User {
    private String Username,phoneNumber,UserUniqueId,Email;
    private ArrayList<DocumentReference> courseCompleted,courseEnrolled;

    public User(String username, String phoneNumber, String userUniqueId, String email, ArrayList<DocumentReference> courseCompleted, ArrayList<DocumentReference> courseEnrolled) {
        Username = username;
        this.phoneNumber = phoneNumber;
        UserUniqueId = userUniqueId;
        Email = email;
        this.courseCompleted = courseCompleted;
        this.courseEnrolled = courseEnrolled;
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
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
