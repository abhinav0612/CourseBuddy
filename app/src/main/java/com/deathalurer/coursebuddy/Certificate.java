package com.deathalurer.coursebuddy;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class Certificate {
    private String courseName,courseIssuer,courseCredentials,courseImage;

    public Certificate(String courseName, String courseIssuer, String courseCredentials, String courseImage) {
        this.courseName = courseName;
        this.courseIssuer = courseIssuer;
        this.courseCredentials = courseCredentials;
        this.courseImage = courseImage;
    }

    public Certificate() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseIssuer() {
        return courseIssuer;
    }

    public void setCourseIssuer(String courseIssuer) {
        this.courseIssuer = courseIssuer;
    }

    public String getCourseCredentials() {
        return courseCredentials;
    }

    public void setCourseCredentials(String courseCredentials) {
        this.courseCredentials = courseCredentials;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }
}
