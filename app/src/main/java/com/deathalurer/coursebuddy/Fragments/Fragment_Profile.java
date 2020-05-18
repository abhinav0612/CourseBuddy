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
import androidx.fragment.app.Fragment;

import com.deathalurer.coursebuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by Abhinav Singh on 10,May,2020
 */
public class Fragment_Profile extends Fragment {
    private static final String TAG = "Fragment_Profile";
    private TextView userName,userEmail,userPhone;
    private ImageView userImage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button editProfile;
    private String userUID;
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
    userImage = view.findViewById(R.id.profilePicture);
    editProfile = view.findViewById(R.id.editProfile);
    mAuth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();
    user = mAuth.getCurrentUser();
    userUID = user.getUid();
        Log.d(TAG, "UID: " + user.getUid() );

    db.collection("Users")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  for(QueryDocumentSnapshot document : task.getResult()){
                      Log.d(TAG, document.getId()+" >> " + document.getString("UserUniqueID"));
                      if(document.getString("UserUniqueID").equals(userUID)){
                          userName.setText(document.getString("Username"));
                          userPhone.setText(document.getString("Phone Number"));
                          userEmail.setText(document.getString("Email"));
                      }
                  }
                }
            });
    editProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Profile_Edit())
                    .commit();
        }
    });
    }
}
