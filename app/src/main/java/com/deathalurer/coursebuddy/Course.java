package com.deathalurer.coursebuddy;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class Course {
    private String CourseName;
    private String CourseDescription;
    private String CourseImage;
    private String CourseIssuer;
    private String CourseEnrolledStudent;


    public Course() {
    }

    public Course(String courseName, String courseDescription, String courseImage, String courseIssuer, String courseEnrolledStudent) {
        CourseName = courseName;
        CourseDescription = courseDescription;
        CourseImage = courseImage;
        CourseIssuer = courseIssuer;
        CourseEnrolledStudent = courseEnrolledStudent;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public String getCourseIssuer() {
        return CourseIssuer;
    }

    public void setCourseIssuer(String courseIssuer) {
        CourseIssuer = courseIssuer;
    }

    public String getCourseEnrolledStudent() {
        return CourseEnrolledStudent;
    }

    public void setCourseEnrolledStudent(String courseEnrolledStudents) {
        CourseEnrolledStudent = courseEnrolledStudents;
    }
}
