package com.deathalurer.coursebuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private TextView userName,userEmail,userPhone;
    private ImageView userImage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button editProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    userName = view.findViewById(R.id.et_userName);
    userEmail = view.findViewById(R.id.et_userEmail);
    userPhone = view.findViewById(R.id.et_userPhone);
    userImage = view.findViewById(R.id.profilePicture);
    editProfile = view.findViewById(R.id.editProfile);
    mAuth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();
    user = mAuth.getCurrentUser();

    db.collection("Users")
            .whereEqualTo("UserUniqueID",user.getUid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                      userName.setText(task.getResult().getDocuments().get(0).get("Username").toString());
                      userEmail.setText(task.getResult().getDocuments().get(0).get("Email").toString());
                      userPhone.setText(task.getResult().getDocuments().get(0).get("Phone Number").toString());
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
