package com.deathalurer.coursebuddy;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class Review {
    private String reviewerName,review;

    public Review() {
    }

    public Review(String reviewerName, String review) {
        this.reviewerName = reviewerName;
        this.review = review;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
