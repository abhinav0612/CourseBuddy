package com.deathalurer.coursebuddy.RecyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {
    private ArrayList<Review> reviews;
    private Context mContext;

    public ReviewRecyclerAdapter(ArrayList<Review> reviews, Context mContext) {
        this.reviews = reviews;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view  = inflater.inflate(R.layout.review_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.reviewerName.setText(review.getReviewerName());
        holder.reviewerReview.setText(review.getReview());
//        Random random = new Random();
//        int color =
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        private TextView reviewerName,reviewerReview;
        private CardView cardView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewerName);
            reviewerReview = itemView.findViewById(R.id.reviewerReview);
            cardView = itemView.findViewById(R.id.reviewCard);
        }
    }
}
