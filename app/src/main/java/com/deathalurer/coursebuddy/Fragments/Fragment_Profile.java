package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deathalurer.coursebuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 10,May,2020
 */
public class Fragment_Profile extends Fragment {
    private static final String TAG = "Fragment_Profile";
    private TextView userName,userEmail,userPhone,userCollege,userBio,enrolledCoursesCount,completedCoursesCount;
    private ImageView userImage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button editProfile;
    private String userUID;
    private CardView viewCertificates,enrolledCard,completedCard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    userName = view.findViewById(R.id.userName);
    userEmail = view.findViewById(R.id.userEmail);
    userPhone = view.findViewById(R.id.userPhone);
    userCollege = view.findViewById(R.id.userCollege);
    userBio = view.findViewById(R.id.userBio);
    userImage = view.findViewById(R.id.profilePicture);
    editProfile = view.findViewById(R.id.editProfile);
    viewCertificates = view.findViewById(R.id.certificatedCard);
    completedCoursesCount = view.findViewById(R.id.courseCompletedCount);
    enrolledCoursesCount = view.findViewById(R.id.courseEnrolledCount);
    enrolledCard = view.findViewById(R.id.enrolledCard);
    completedCard = view.findViewById(R.id.completedCard);

    mAuth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();
    user = mAuth.getCurrentUser();
    userUID = user.getUid();

    getData();

    editProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Profile_Edit())
                    .commit();
        }
    });

    viewCertificates.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Certificate())
                    .addToBackStack(null)
                    .commit();
        }
    });
    }
    void getData(){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Log.d(TAG, document.getId()+" >> " + document.getString("UserUniqueID"));
                            if(document.getString("UserUniqueID").equals(userUID)){
                                userName.setText(document.getString("Username"));
                                userPhone.setText(document.getString("phoneNumber"));
                                userEmail.setText(document.getString("email"));
                                userBio.setText(document.getString("bio"));
                                userCollege.setText(document.getString("college"));
                                Glide.with(getContext()).load(document.getString("profileImage"))
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(userImage);
                                ArrayList<DocumentReference> completedList = (ArrayList<DocumentReference>) document.get("courseCompleted");
                                ArrayList<DocumentReference> enrolledList = (ArrayList<DocumentReference>) document.get("courseCompleted");
                                enrolledCoursesCount.setText("Course Enrolled: "+ enrolledList.size()+"");
                                completedCoursesCount.setText("Course Completed: "+ completedList.size() +"");
                                enrolledCard.setVisibility(View.VISIBLE);
                                completedCard.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }
}
