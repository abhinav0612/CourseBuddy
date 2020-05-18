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
import com.deathalurer.coursebuddy.RecyclerViewAdapters.CoursesAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class Fragment_Home extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Course> coursesList = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.HomeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        db.collection("Courses")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots==null)
                        {
                            return;
                        }
                        else {
                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                            {
                                Log.e("______",snapshot.getData().toString());
                                Course c = snapshot.toObject(Course.class);
                                coursesList.add(c);
                            }
                        }

                        CoursesAdapter adapter = new CoursesAdapter(coursesList,getContext(),getFragmentManager());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });


    }
}
