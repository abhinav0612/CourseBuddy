package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deathalurer.coursebuddy.EnrolledStudent;
import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.EnrolledCoursesAdapter;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.EnrolledStudentAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 20,May,2020
 */
public class Enrolled_Student_Fragment extends Fragment {
    private static final String TAG = "Student_Fragment";
    private ArrayList<EnrolledStudent> studentsList;
    private RelativeLayout filterResults;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private  EnrolledStudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enrolled_student_list_fargment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.enrolledStudentRecyclerView);
        filterResults = view.findViewById(R.id.filterResultsLayout);
        studentsList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection("Courses")
                .document(getArguments().getString("DocId"))
                .collection("Endrolled")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots !=  null){
                            for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                final String collegeName = snapshot.getString("collegeName");
                                final DocumentReference reference = (DocumentReference) snapshot.get("student");
                                Log.i(TAG,collegeName + " "+reference.getId());
                                db.collection("Users").document(reference.getId())
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Log.i(TAG,documentSnapshot.getData().toString());
                                                Log.i(TAG,documentSnapshot.getString("Username"));
                                                EnrolledStudent student = new EnrolledStudent(documentSnapshot.getString("Username"),collegeName,reference);
                                                studentsList.add(student);
                                                Log.i(TAG,student.getStudentName() + " " + student.getStudentCollege());
                                                if(studentsList.size() == 1){
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                    adapter = new EnrolledStudentAdapter(studentsList,getContext(),getFragmentManager());
                                                    recyclerView.setAdapter(adapter);
                                                }
                                                if(adapter != null)
                                                    adapter.notifyDataSetChanged();
                                            }
                                        });


                            }
                        }
                        else
                            Log.i(TAG,"snapshot null");
                    }
                });


    }
}
