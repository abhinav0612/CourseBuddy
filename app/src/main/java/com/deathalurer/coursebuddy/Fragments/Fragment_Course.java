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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deathalurer.coursebuddy.Course;
import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.ReviewRecyclerAdapter;
import com.deathalurer.coursebuddy.Review;
import com.deathalurer.coursebuddy.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class Fragment_Course extends Fragment {
    private TextView courseName,courseDescription,courseEnrolledCount,reviewTextView,courseIssuer,courseEnrolledButton,courseCompletedButton;
    private ImageView courseImage;
    private View customDemoView;
    private RecyclerView recyclerView;
    private ArrayList<Review> reviews = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String docId;
    private String course_name;
    private CardView enrolledCard;
    private ConstraintLayout twoWayButton;
    private DocumentReference docReference;
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
        reviewTextView = view.findViewById(R.id.demoReview);
        customDemoView = view.findViewById(R.id.demoView);
        recyclerView = view.findViewById(R.id.reviewsRecyclerView);
        enrolledCard = view.findViewById(R.id.cardViewEnrolled);
        courseEnrolledButton = view.findViewById(R.id.layoutEnrolledTextView);
        courseCompletedButton = view.findViewById(R.id.layoutCompletedTextView);
        twoWayButton = view.findViewById(R.id.twoWayButton);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
       getData();

       // for fetching enrolled students
        courseEnrolledCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enrolled_Student_Fragment fragment = new Enrolled_Student_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("CourseName",course_name);
                bundle.putString("DocId",docId);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        //for already enrolled or completed condition

        courseEnrolledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Users")
                        .whereEqualTo("UserUniqueID",mUser.getUid())
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                            ArrayList<DocumentReference> list = (ArrayList<DocumentReference>) snapshot.get("courseEnrolled");
                            if (list != null){
                                docReference = db.collection("Courses")
                                        .document(docId);
                                list.add(docReference);
                                db.collection("Users")
                                        .document(snapshot.getId())
                                        .update("courseEnrolled",list)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(),"Hurray! Course added as enrolled.",Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                            }
                            else{
                                list = new ArrayList<>();
                                docReference = db.collection("Courses")
                                        .document(docId);
                                list.add(docReference);
                                db.collection("Users")
                                        .document(snapshot.getId())
                                        .update("courseEnrolled",list)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(),"Hurray! Course added as enrolled.",Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                            }

                        }
                    }
                });
            }
        });

        courseCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Users")
                        .whereEqualTo("UserUniqueID",mUser.getUid())
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                            ArrayList<DocumentReference> list = (ArrayList<DocumentReference>) snapshot.get("courseCompleted");
                            if (list != null){
                                docReference = db.collection("Courses")
                                        .document(docId);
                                list.add(docReference);
                                db.collection("Users")
                                        .document(snapshot.getId())
                                        .update("courseCompleted",list)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(),"Hurray! Course added as completed.",Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                            }
                            else
                            {
                                list = new ArrayList<>();
                                docReference = db.collection("Courses")
                                        .document(docId);
                                list.add(docReference);
                                db.collection("Users")
                                        .document(snapshot.getId())
                                        .update("courseCompleted",list)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(),"Hurray! Course added as completed.",Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                            }

                        }
                    }
                });
            }
        });


    }
    void getData(){
        course_name = getArguments().getString("CourseName");
        db = FirebaseFirestore.getInstance();
        db.collection("Courses")
                .whereEqualTo("CourseName",course_name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                            Course c = snapshots.toObject(Course.class);
                            courseName.setText(c.getCourseName());
                            courseDescription.setText(c.getCourseDescription());
                            courseIssuer.setText(c.getCourseIssuer());
//                            courseEnrolledCount.setText("Total Students Enrolled: "+c.getCourseEnrolledStudent());
                            customDemoView.setVisibility(View.VISIBLE);
                            reviewTextView.setVisibility(View.VISIBLE);
                            Glide.with(getContext()).load(c.getCourseImage()).into(courseImage);
                            docId = snapshots.getId();

                            snapshots.getReference().collection("Endrolled")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            task.getResult().size();
                                            courseEnrolledCount.setText("Total Students Enrolled: "+task.getResult().size());
                                            enrolledCard.setVisibility(View.VISIBLE);
                                        }
                                    });

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
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                                                ReviewRecyclerAdapter adapter = new ReviewRecyclerAdapter(reviews,getContext());
                                                recyclerView.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                                twoWayButton.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });


                        }
                    }
                });
    }
}
