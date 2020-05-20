package com.deathalurer.coursebuddy;

import com.google.firebase.firestore.DocumentReference;

/**
 * Created by Abhinav Singh on 20,May,2020
 */
public class EnrolledStudent {
    private String studentName,studentCollege;
    private DocumentReference studentReference;

    public EnrolledStudent(String studentName, String studentCollege,DocumentReference studentReference) {
        this.studentName = studentName;
        this.studentCollege = studentCollege;
        this.studentReference = studentReference;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCollege() {
        return studentCollege;
    }

    public void setStudentCollege(String studentCollege) {
        this.studentCollege = studentCollege;
    }

    public DocumentReference getStudentReference() {
        return studentReference;
    }

    public void setStudentReference(DocumentReference studentReference) {
        this.studentReference = studentReference;
    }
}
