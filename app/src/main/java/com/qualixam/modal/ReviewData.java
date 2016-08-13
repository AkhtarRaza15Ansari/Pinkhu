package com.qualixam.modal;

/**
 * Created by akhtarraza on 11/02/16.
 */
public class ReviewData {
    private String ReviewID;
    private String LocationID;
    private String Rating;
    private String Reviews;

    public ReviewData(String ReviewID, String LocationID, String Rating, String Reviews){
        this.ReviewID = ReviewID;
        this.LocationID = LocationID;
        this.Rating = Rating;
        this.Reviews = Reviews;
    }
    public String getReviewID(){
        return ReviewID;
    }
    public void setReviewID(String ReviewID){
        this.ReviewID = ReviewID;
    }
    public String getLocationID() {
        return LocationID;
    }
    public void setLocationID(String LocationID) {
        this.LocationID = LocationID;
    }
    public String getRating() {
        return Rating;
    }
    public void setRating(String Rating) {
        this.Rating = Rating;
    }
    public String getReviews()
    {
        return Reviews;
    }
    public void setReviews(String Reviews)
    {
        this.Reviews = Reviews;
    }
}