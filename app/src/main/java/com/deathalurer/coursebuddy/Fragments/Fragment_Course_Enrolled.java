package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deathalurer.coursebuddy.Course;
import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.EnrolledCoursesAdapter;
import com.deathalurer.coursebuddy.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class Fragment_Course_Enrolled extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ArrayList<Course> courseList;
    private RecyclerView recyclerView;
    private EnrolledCoursesAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_enrolled_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.courseEnrolledRecycler);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        getData();
    }

    void getData(){
        db.collection("Users")
                .whereEqualTo("UserUniqueID",mUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots!=null){
                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                ArrayList<DocumentReference> list = (ArrayList<DocumentReference>) snapshot.get("courseEnrolled");
                                courseList = new ArrayList<>();
                                for (DocumentReference documentReference : list){
                                    db.collection("Courses")
                                            .document(documentReference.getId())
                                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Log.e("___________________",documentSnapshot.getData()+"");
                                            courseList.add(documentSnapshot.toObject(Course.class));
                                            if(courseList.size()==1){
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                adapter = new EnrolledCoursesAdapter(courseList,getContext(),getActivity().getSupportFragmentManager());
                                                recyclerView.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }
                                            else
                                                adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
    }

}
