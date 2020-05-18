package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deathalurer.coursebuddy.Course;
import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.ReviewRecyclerAdapter;
import com.deathalurer.coursebuddy.Review;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class Fragment_Course extends Fragment {
    private TextView courseName,courseDescription,courseEnrolledCount,curseIssuer,courseIssuer;
    private ImageView courseImage;
    private RecyclerView recyclerView;
    private ArrayList<Review> reviews = new ArrayList<>();
    private FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.course_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseName = view.findViewById(R.id.courseTitle);
        courseDescription = view.findViewById(R.id.courseDescription);
        courseEnrolledCount = view.findViewById(R.id.courseTotalEnrolled);
        courseImage = view.findViewById(R.id.courseImage);
        courseIssuer = view.findViewById(R.id.courseIssuer);
        recyclerView = view.findViewById(R.id.reviewsRecyclerView);

        db = FirebaseFirestore.getInstance();
        db.collection("Courses")
                .whereEqualTo("CourseName",getArguments().getString("CourseName"))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                            Course c = snapshots.toObject(Course.class);
                            courseName.setText(c.getCourseName());
                            courseDescription.setText(c.getCourseDescription());
                            courseIssuer.setText(c.getCourseIssuer());
                            courseEnrolledCount.setText("Total Students Enrolled: "+c.getCourseEnrolledStudent());
                            Glide.with(getContext()).load(c.getCourseImage()).into(courseImage);

                            db.collection("Courses")
                                    .document(snapshots.getId())
                                    .collection("Reviews")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if(queryDocumentSnapshots != null){
                                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                                    Review r = documentSnapshot.toObject(Review.class);
                                                    reviews.add(r);
                                                }
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                ReviewRecyclerAdapter adapter = new ReviewRecyclerAdapter(reviews,getContext());
                                                recyclerView.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    });

                        }
                    }
                });


    }
}
